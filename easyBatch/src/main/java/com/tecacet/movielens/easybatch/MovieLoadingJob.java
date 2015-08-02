package com.tecacet.movielens.easybatch;

import java.io.File;
import java.io.FileNotFoundException;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;
import com.tecacet.movielens.easybathc.converter.LocalDateTypeConverter;
import com.tecacet.movielens.model.Movie;

@Component
public class MovieLoadingJob {

    private static final String MOVIE_FILENAME = "../ml-100k/u.item";

    private final MovieLoadingProcessor movieProcessor;

    @Autowired
    public MovieLoadingJob(MovieLoadingProcessor movieProcessor) {
        super();
        this.movieProcessor = movieProcessor;
    }

    public Report readMovies() throws Exception {
        Engine engine = buildEngine();
        return engine.call();
    }

    private Engine buildEngine() throws FileNotFoundException {
        DelimitedRecordMapper<Movie> recordMapper = new DelimitedRecordMapper<Movie>(Movie.class, new Integer[] { 0, 1, 2, 3, 4 }, new String[] {
                "id", "title", "releaseDate", "videoReleaseDate", "IMDBurl" });
        recordMapper.setDelimiter("|");
        recordMapper.registerTypeConverter(new LocalDateTypeConverter());
        Engine engine = new EngineBuilder().enableJMX(true).reader(new FlatFileRecordReader(new File(MOVIE_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<Movie>()).processor(movieProcessor).build();
        return engine;
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class, SpringConfig.class);
        MovieLoadingJob job = context.getBean(MovieLoadingJob.class);
        job.readMovies();
        

    }
}
