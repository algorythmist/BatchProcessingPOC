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
import com.tecacet.movielens.repository.MovieRepository;
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
	

}
