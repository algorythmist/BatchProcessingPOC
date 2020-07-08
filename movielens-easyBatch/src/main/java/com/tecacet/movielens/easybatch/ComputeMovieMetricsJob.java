package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.repository.UserRatingRepository;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobMetrics;
import org.easybatch.core.job.JobReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ComputeMovieMetricsJob {

    private final UserRatingRepository ratingRepository;
    private final JobExecutor jobExecutor = new JobExecutor();

    @Autowired
    public ComputeMovieMetricsJob(UserRatingRepository repository) {
        super();
        this.ratingRepository = repository;
    }

    public Map<String, Object> computeMetrics() throws IOException {
        Job job = buildJob();
        JobReport report = jobExecutor.execute(job);
        JobMetrics jobMetrics = report.getMetrics();
        return jobMetrics.getCustomMetrics();
    }

    private Job buildJob() {
        MongoDBStreamReader reader = new MongoDBStreamReader(ratingRepository);
        return new JobBuilder().enableJmx(true).reader(reader).processor(new MovieRatingProcessor()).build();
    }

}
