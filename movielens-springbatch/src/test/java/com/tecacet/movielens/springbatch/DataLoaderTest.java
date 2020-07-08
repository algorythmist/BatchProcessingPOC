package com.tecacet.movielens.springbatch;

import static org.junit.Assert.assertEquals;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.repository.MovieRepository;
import com.tecacet.movielens.repository.UserRatingRepository;
import com.tecacet.movielens.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BatchConfig.class, MongoConfig.class})
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
        assertEquals(1682, movieRepository.count());
        movieRepository.deleteAll();
    }

    @Test
    public void testLoadUsers() throws Exception {
        JobExecution jobExecution = dataLoader.loadUsers();
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals(943, userRepository.count());
        userRepository.deleteAll();
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
        assertEquals(100000, userRatingRepository.count());
        userRatingRepository.deleteAll();
    }

}
