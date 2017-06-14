package com.tecacet.movielens.easybatch;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.Movie;
import com.tecacet.movielens.repository.MovieRepository;

@Component
public class MovieLoadingProcessor implements RecordProcessor<Record<Movie>, Record<Movie>> {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieLoadingProcessor(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    @Override
    public Record<Movie> processRecord(Record<Movie> record)  {
        movieRepository.save(record.getPayload());
        return record;
    }

}
