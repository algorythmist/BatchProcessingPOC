package com.tecacet.movielens.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tecacet.movielens.model.User;
import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.UserRatingRepository;
import com.tecacet.movielens.repository.UserRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public JobRepository jobRepository() throws Exception {
		return new MapJobRepositoryFactoryBean().getJobRepository();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository)
			throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}


	@Bean
	public ItemProcessor<?, ?> processor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	public ItemWriter<User> userWritter(UserRepository userRepository) {
		RepositoryItemWriter<User> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(userRepository);
		itemWriter.setMethodName("save");
		return itemWriter;
	}
	
	@Bean
	public ItemWriter<UserRating> ratingWritter(UserRatingRepository repository) {
		RepositoryItemWriter<UserRating> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(repository);
		itemWriter.setMethodName("save");
		return itemWriter;
	}

	@Bean
	public Job importUserJob(JobBuilderFactory jobs, Step importUserStep) {
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer())
				//.listener(listener)
				.flow(importUserStep).end().build();
	}

	@Bean
	public Job importRatingsJob(JobBuilderFactory jobs, Step importRatingStep) {
		return jobs.get("importRatingsJob").incrementer(new RunIdIncrementer())
				//.listener(listener)
				.flow(importRatingStep).end().build();
	}
	
	@Bean(name="importUserStep")
	public Step importUserStep(StepBuilderFactory stepBuilderFactory,
			UserItemReader reader, ItemWriter<User> writer,
			ItemProcessor<User, User> processor) {
		return stepBuilderFactory.get("importUserStep").<User, User> chunk(10)
				.reader(reader).processor(processor).writer(writer).build();
	}
	
	@Bean(name="importRatingStep")
	public Step importRatingStep(StepBuilderFactory stepBuilderFactory,
			RatingItemReader reader, ItemWriter<UserRating> writer,
			ItemProcessor<UserRating, UserRating> processor) {
		return stepBuilderFactory.get("importRatingStep").<UserRating, UserRating> chunk(100)
				.reader(reader).processor(processor).writer(writer).build();
	}

}