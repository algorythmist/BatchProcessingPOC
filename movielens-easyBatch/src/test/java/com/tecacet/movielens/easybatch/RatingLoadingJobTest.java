package com.tecacet.movielens.easybatch;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.easybatch.core.job.JobReport;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;
import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.UserRatingRepository;

//TODO
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class, MongoConfig.class })
public class RatingLoadingJobTest {

	@Resource
	private RatingLoadingJob ratingLoadingJob;

	@Resource
	private UserRatingRepository userRatingRepository;

	@Test
	public void testReadRatings() throws IOException {
		JobReport jobReport = ratingLoadingJob.readRatings();
		System.out.println(jobReport);
		List<UserRating> ratings = userRatingRepository.findAll();
		System.out.println(ratings);
		userRatingRepository.delete(ratings);
	}

}