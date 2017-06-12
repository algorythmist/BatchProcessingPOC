package com.tecacet.movielens.springbatch;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.MongoConfig;

@Component
public class DataLoader {

    @Resource(name = "importUserJob")
    private Job userJob;

    @Resource(name = "importMovieJob")
    private Job movieJob;

    @Resource(name = "importRatingsJob")
    private Job ratingJob;

    @Resource
    private JobLauncher jobLauncher;

    public JobExecution loadUsers() throws Exception {
        return jobLauncher.run(userJob, new JobParameters());
    }
    
    public JobExecution loadMovies() throws Exception {
        return jobLauncher.run(movieJob, new JobParameters());
    }

    public JobExecution loadRatings() throws Exception {
        return jobLauncher.run(ratingJob, new JobParameters());
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class, BatchConfig.class);
        DataLoader loader = context.getBean(DataLoader.class);
        loader.loadUsers();
        loader.loadMovies();
        loader.loadRatings();
    }
}
