package com.tecacet.movielens.easybatch;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.record.Record;
import org.easybatch.core.dispatcher.PoisonRecordBroadcaster;
import org.easybatch.core.dispatcher.RoundRobinRecordDispatcher;
import org.easybatch.core.filter.PoisonRecordFilter;
import org.easybatch.core.reader.BlockingQueueRecordReader;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tools.reporting.DefaultJobReportMerger;
import org.easybatch.tools.reporting.JobReportMerger;
import org.easybatch.validation.BeanValidationRecordValidator;

import com.tecacet.movielens.model.User;
import com.tecacet.movielens.model.UserRating;

public class ParallelTutorialWithRecordDispatching {

	private static final String DATA_FILENAME = "../ml-100k/u.data";
	private static final int THREAD_POOL_SIZE = 4;

	static DelimitedRecordMapper recordMapper = RatingLoadingJob
			.getRatingRecordMapper();

	public static void main(String[] args) throws Exception {

		File userFile = new File(DATA_FILENAME);

		// Create queues
		BlockingQueue<Record> queue1 = new LinkedBlockingQueue<>();
		BlockingQueue<Record> queue2 = new LinkedBlockingQueue<>();

		// Create a round robin record dispatcher
		RoundRobinRecordDispatcher roundRobinRecordDispatcher = new RoundRobinRecordDispatcher<>(
				Arrays.asList(queue1, queue2));

		// Build a master job that will read records from the data source
		// and dispatch them to workers
		Job masterJob = JobBuilder
				.aNewJob()
				.named("master-engine")
				.reader(new FlatFileRecordReader(userFile))
				.processor(roundRobinRecordDispatcher)
				.jobListener(
						new PoisonRecordBroadcaster<>(Arrays.asList(queue1, queue2)))
				.build();

		// Build worker engines
		Job workerJob1 = buildWorkerJob(queue1, "worker-job1");
		Job workerJob2 = buildWorkerJob(queue2, "worker-job2");

		// Create a thread pool to call master and worker jobs in parallel
		ExecutorService executorService = Executors
				.newFixedThreadPool(THREAD_POOL_SIZE);

		// Submit workers to executor service
		List<Future<JobReport>> partialReports = executorService.invokeAll(Arrays
				.asList(masterJob, workerJob1, workerJob2));
		// merge partial reports into a global one
		JobReport report1 = partialReports.get(0).get();
		JobReport report2 = partialReports.get(1).get();

		JobReportMerger reportMerger = new DefaultJobReportMerger();
		JobReport finalReport = reportMerger.mergerReports(report1, report2);
		System.out.println(finalReport);

		// Shutdown executor service
		executorService.shutdown();

	}

	private static Job buildWorkerJob(BlockingQueue<Record> queue,
			String jobName) {
		return JobBuilder.aNewJob().named(jobName)
				.reader(new BlockingQueueRecordReader<>(queue)).mapper(recordMapper)
				.validator(new BeanValidationRecordValidator<User>())
				.filter(new PoisonRecordFilter())
				.processor(new MovieRatingProcessor()).build();
	}

}
