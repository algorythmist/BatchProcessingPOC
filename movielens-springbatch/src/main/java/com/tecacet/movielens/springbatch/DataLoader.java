package com.tecacet.movielens.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

}
