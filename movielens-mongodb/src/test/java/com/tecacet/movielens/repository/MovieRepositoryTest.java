package com.tecacet.movielens.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.model.Movie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void test() {
        Movie movie = new Movie();
        movie.setReleaseDate(LocalDate.of(2010, 3, 2));
        movie.setTitle("1984");
        movieRepository.save(movie);
        assertEquals(1, movieRepository.count());

        Movie found = movieRepository.findOne(movie.getId());
        assertEquals("1984", found.getTitle());
        assertEquals("2010-03-02", found.getReleaseDate().toString());

        movieRepository.delete(movie);
        assertEquals(0, movieRepository.count());
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void testInvalidMovie() {
    	 Movie movie = new Movie();
         movie.setTitle("");
         movieRepository.save(movie);
    }
}
