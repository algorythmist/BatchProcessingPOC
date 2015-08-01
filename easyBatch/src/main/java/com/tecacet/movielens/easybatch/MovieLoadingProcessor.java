package com.tecacet.movielens.easybatch;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.Movie;
import com.tecacet.movielens.repository.MovieRepository;

@Component
public class MovieLoadingProcessor implements RecordProcessor<Movie, Movie> {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieLoadingProcessor(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie processRecord(Movie movie) throws RecordProcessingException {
        movieRepository.save(movie);
        return movie;
    }

}
