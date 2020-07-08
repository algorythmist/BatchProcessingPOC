package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.easybathc.converter.GenderTypeConverter;
import com.tecacet.movielens.easybathc.converter.OccupationTypeConverter;
import com.tecacet.movielens.model.UserRating;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobReport;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class RatingLoadingJob {

    private static final String RATING_FILENAME = "../ml-100k/u.data";

    private final RatingLoadingProcessor ratingLoadingProcessor;

    @Autowired
    public RatingLoadingJob(RatingLoadingProcessor ratingLoadingProcessor) {
        super();
        this.ratingLoadingProcessor = ratingLoadingProcessor;
    }

    public JobReport readRatings() throws IOException {
        Job job = buildJob();
        return job.call();
    }

    private Job buildJob() throws IOException {
        DelimitedRecordMapper recordMapper = getRatingRecordMapper();
        return new JobBuilder().enableJmx(true).reader(new FlatFileRecordReader(new File(RATING_FILENAME)))
                .mapper(recordMapper).validator(new BeanValidationRecordValidator())
                .processor(ratingLoadingProcessor).build();
    }

    private static DelimitedRecordMapper getRatingRecordMapper() {
        DelimitedRecordMapper recordMapper = new DelimitedRecordMapper(UserRating.class, "userId", "itemId", "rating",
                "timestamp");
        recordMapper.setDelimiter("\\s");
        recordMapper.registerTypeConverter(new GenderTypeConverter());
        recordMapper.registerTypeConverter(new OccupationTypeConverter());
        return recordMapper;
    }

}
