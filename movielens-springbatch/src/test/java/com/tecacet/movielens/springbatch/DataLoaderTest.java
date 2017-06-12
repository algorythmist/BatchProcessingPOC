package com.tecacet.movielens.springbatch;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.model.Movie;
import com.tecacet.movielens.model.User;
import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.MovieRepository;
import com.tecacet.movielens.repository.UserRatingRepository;
import com.tecacet.movielens.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchConfig.class, MongoConfig.class })
public class DataLoaderTest {

	@Resource
	private DataLoader dataLoader;

	@Resource
	private MovieRepository movieRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserRatingRepository userRatingRepository;

	@Test
	public void testLoadMovies() throws Exception {
		JobExecution jobExecution = dataLoader.loadMovies();
		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		List<Movie> movies = movieRepository.findAll();
		assertEquals(1682, movies.size());
		movieRepository.delete(movies);
	}

	@Test
	public void testLoadUsers() throws Exception {
		JobExecution jobExecution = dataLoader.loadUsers();
		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		List<User> users = userRepository.findAll();
		assertEquals(943, users.size());
		userRepository.delete(users);
	}

	@Test
	public void testLoadRatings() throws Exception {
		// clean up first
		userRatingRepository.delete(userRatingRepository.findAll());

		long startTime = System.currentTimeMillis();
		JobExecution jobExecution = dataLoader.loadRatings();
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("Time to load = " + duration);
		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		List<UserRating> ratings = userRatingRepository.findAll();
		assertEquals(100000, ratings.size());
		userRatingRepository.delete(ratings);
	}

}
