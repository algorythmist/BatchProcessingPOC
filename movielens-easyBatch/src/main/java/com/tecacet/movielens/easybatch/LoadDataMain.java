package com.tecacet.movielens.easybatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;

public class LoadDataMain {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class, SpringConfig.class);
        MovieLoadingJob movieLoadingJob = context.getBean(MovieLoadingJob.class);
        RatingLoadingJob ratingLoadigJob = context.getBean(RatingLoadingJob.class);
        UserLoadingJob userLoadingJob = context.getBean(UserLoadingJob.class);
        movieLoadingJob.readMovies();
        userLoadingJob.readUsers();
        ratingLoadigJob.readRatings();
    }
}
