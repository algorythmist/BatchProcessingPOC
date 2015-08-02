package com.tecacet.movielens.easybatch;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Record;
import org.easybatch.core.api.Report;
import org.easybatch.core.dispatcher.PoisonRecordBroadcaster;
import org.easybatch.core.dispatcher.RoundRobinRecordDispatcher;
import org.easybatch.core.filter.PoisonRecordFilter;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.core.reader.QueueRecordReader;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tools.reporting.DefaultReportMerger;
import org.easybatch.tools.reporting.ReportMerger;
import org.easybatch.validation.BeanValidationRecordValidator;

import com.tecacet.movielens.model.User;

public class ParallelTutorialWithRecordDispatching {

    private static final String USER_FILENAME = "../ml-100k/u.user";
    private static final int THREAD_POOL_SIZE = 4;

    static DelimitedRecordMapper<User> recordMapper = new DelimitedRecordMapper<User>(User.class, new String[]{"id","age","gender","occupation","zipCode"});
    
    static {
        recordMapper.setDelimiter("|");
    }
    
    public static void main(String[] args) throws Exception {

        // Input file tweets.csv
        File userFile = new File(USER_FILENAME);

        // Create queues
        BlockingQueue<Record> queue1 = new LinkedBlockingQueue<Record>();
        BlockingQueue<Record> queue2 = new LinkedBlockingQueue<Record>();

        // Create a round robin record dispatcher
        RoundRobinRecordDispatcher roundRobinRecordDispatcher = 
                new RoundRobinRecordDispatcher(Arrays.asList(queue1, queue2));

      
        // Build a master engine that will read records from the data source
        // and dispatch them to worker engines
        Engine masterEngine = EngineBuilder.aNewEngine()
                .named("master-engine")
                .reader(new FlatFileRecordReader(userFile))
                .processor(roundRobinRecordDispatcher)
                .jobEventListener(new PoisonRecordBroadcaster(roundRobinRecordDispatcher)).build();

        // Build worker engines
        Engine workerEngine1 = buildWorkerEngine(queue1, "worker-engine1");
        Engine workerEngine2 = buildWorkerEngine(queue2, "worker-engine2");

        // Create a thread pool to call master and worker engines in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // Submit workers to executor service
        List<Future<Report>> partialReports = executorService.invokeAll(Arrays.asList(masterEngine, workerEngine1, workerEngine2));
      //merge partial reports into a global one
        Report report1 = partialReports.get(0).get();
        Report report2 = partialReports.get(1).get();

        ReportMerger reportMerger = new DefaultReportMerger();
        Report finalReport = reportMerger.mergerReports(report1, report2);
        System.out.println(finalReport);
        
        // Shutdown executor service
        executorService.shutdown();
        
        
        
    }

    private static Engine buildWorkerEngine(BlockingQueue<Record> queue, String engineName) {
        return EngineBuilder.aNewEngine().named(engineName)
                .reader(new QueueRecordReader(queue))
                .mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<User>())
                .filter(new PoisonRecordFilter()).processor(new MovieRatingProcessor())
                .build();
    }

}
