package com.tecacet.movielens.easybatch;

import java.io.File;
import java.io.FileNotFoundException;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Record;
import org.easybatch.core.api.RecordMapper;
import org.easybatch.core.api.RecordMappingException;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.easybathc.converter.LocalDateTypeConverter;
import com.tecacet.movielens.model.Movie;

@Component
public class MovieLoadingJob {

    private static final String MOVIE_FILENAME = "../ml-100k/u.item";

    private final MovieLoadingProcessor movieProcessor;

    class DelegatingRecordMapper implements RecordMapper<Movie> {

        private DelimitedRecordMapper<Movie> recordMapper = new DelimitedRecordMapper<Movie>(Movie.class, new Integer[] { 0, 1, 2, 3, 4 },
                new String[] { "id", "title", "releaseDate", "videoReleaseDate", "IMDBurl" });

        public DelegatingRecordMapper() {
            recordMapper.setDelimiter("|");
            recordMapper.registerTypeConverter(new LocalDateTypeConverter());
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Movie mapRecord(Record record) throws RecordMappingException {
            Movie movie = recordMapper.mapRecord(record);
            String[] tokens = ((String) record.getPayload()).split("\\|");
            for (int i = 5; i < 24; i++) {
                int index = Integer.parseInt(tokens[i]);
                if (index == 1) {
                    movie.addGenre(i-5);
                }
            }
            return movie;
        }

    }

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

        RecordMapper<Movie> recordMapper = new DelegatingRecordMapper();
        Engine engine = new EngineBuilder().enableJMX(true).reader(new FlatFileRecordReader(new File(MOVIE_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<Movie>()).processor(movieProcessor).build();
        return engine;
    }

 
}
