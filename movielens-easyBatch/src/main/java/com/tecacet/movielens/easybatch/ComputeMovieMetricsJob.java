package com.tecacet.movielens.easybatch;

import java.io.IOException;
import java.util.Map;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.repository.UserRatingRepository;

@Component
public class ComputeMovieMetricsJob {

	private final UserRatingRepository ratingRepository;

	@Autowired
	public ComputeMovieMetricsJob(UserRatingRepository repository) {
		super();
		this.ratingRepository = repository;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, MovieMetrics> computeMetrics() throws IOException {
		Job job = buildJob();
		JobReport report = JobExecutor.execute(job);
		return (Map<Long, MovieMetrics>) report.getResult();
	}

	private Job buildJob() throws IOException {
		MongoDBStreamReader reader = new MongoDBStreamReader(ratingRepository);
		return new JobBuilder().jmxMode(true).reader(reader).processor(new MovieRatingProcessor()).build();
	}

}
