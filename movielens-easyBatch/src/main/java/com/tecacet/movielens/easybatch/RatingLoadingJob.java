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
import org.springframework.stereotype.Component;

import com.tecacet.movielens.easybathc.converter.GenderTypeConverter;
import com.tecacet.movielens.easybathc.converter.OccupationTypeConverter;
import com.tecacet.movielens.model.UserRating;

@Component
public class RatingLoadingJob {

    private static final String RATING_FILENAME = "../ml-100k/u.data";

    private final RatingLoadingProcessor ratingLoadingProcessor;

   
    
    @Autowired
    public RatingLoadingJob(RatingLoadingProcessor ratingLoadingProcessor) {
        super();
        this.ratingLoadingProcessor = ratingLoadingProcessor;
    }

    public Report readRatings() throws Exception {
        Engine engine = buildEngine();
        return engine.call();

    }
  
    private Engine buildEngine() throws FileNotFoundException {
        DelimitedRecordMapper<UserRating> recordMapper = new DelimitedRecordMapper<UserRating>(UserRating.class,
                new String[] { "userId", "itemId", "rating", "timestamp"});
        recordMapper.setDelimiter("\\s");
        recordMapper.registerTypeConverter(new GenderTypeConverter());
        recordMapper.registerTypeConverter(new OccupationTypeConverter());
        Engine engine = new EngineBuilder().enableJMX(true).reader(new FlatFileRecordReader(new File(RATING_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<UserRating>()).processor(ratingLoadingProcessor).build();
        return engine;
    }

 
}
