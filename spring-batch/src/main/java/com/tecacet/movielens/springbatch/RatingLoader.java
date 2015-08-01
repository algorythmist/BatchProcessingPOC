package com.tecacet.movielens.springbatch;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.MongoConfig;

@Component
public class RatingLoader {
	
	@Resource(name="importRatingsJob")
	private Job job;
	
	@Resource
	private JobLauncher jobLauncher;
	
	public void load() throws Exception {
		jobLauncher.run(job, new JobParameters());
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				MongoConfig.class, BatchConfiguration.class);
		RatingLoader userLoader = context.getBean(RatingLoader.class);
		userLoader.load();
	}
}
