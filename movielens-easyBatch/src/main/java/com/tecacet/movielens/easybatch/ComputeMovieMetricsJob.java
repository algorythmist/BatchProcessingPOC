package com.tecacet.movielens.easybatch;

import java.io.FileNotFoundException;
import java.util.Map;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.job.JobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;
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
    public Map<Long, MovieMetrics> computeMetrics() throws Exception {
        Job job = buildJob();
        JobReport report = JobExecutor.execute(job);
        return (Map<Long, MovieMetrics>) report.getResult();
    }

    private Job buildJob() throws FileNotFoundException {

        MongoDBStreamReader reader = new MongoDBStreamReader(ratingRepository);

        Job job = new JobBuilder()
                .jmxMode(true)
                .reader(reader)
                //.mapper(new GenericRecordMapper()) No need for this mapper anymore, all components now operate on Record (more consistent API and workflow)
                .processor(new MovieRatingProcessor())
                .build();
        return job;
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class, SpringConfig.class);
        ComputeMovieMetricsJob job = context.getBean(ComputeMovieMetricsJob.class);
        MovieMetricsFileWriter fileWriter = context.getBean(MovieMetricsFileWriter.class);
        Map<Long, MovieMetrics> map =job.computeMetrics();
        fileWriter.writeMetrics(map);
    }

}
