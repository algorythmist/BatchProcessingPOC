package com.tecacet.easybatch;

import java.io.File;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;

import com.tecacet.easybatch.converter.LocalDateTypeConverter;
import com.tecacet.movielens.model.Movie;

public class MovieLoadingJob {

    private static final String MOVIE_FILENAME = "../ml-100k/u.item";

    public <T> void readMovies() throws Exception {

        DelimitedRecordMapper<Movie> recordMapper = 
                new DelimitedRecordMapper<Movie>(Movie.class, new Integer[] { 0, 1, 2, 3, 4 }, new String[] { "id",
                "title", "releaseDate", "videoReleaseDate", "IMDBurl" });
        recordMapper.setDelimiter("|");
        recordMapper.registerTypeConverter(new LocalDateTypeConverter());
        Engine engine = new EngineBuilder().enableJMX(true).reader(new FlatFileRecordReader(new File(MOVIE_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<Movie>()).processor(new MovieProcessor())
                .recordProcessorEventListener(new LoggingEventListener()).build();
        Report report = engine.call();

    }

    public static void main(String[] args) throws Exception {
        new MovieLoadingJob().readMovies();

    }
}
