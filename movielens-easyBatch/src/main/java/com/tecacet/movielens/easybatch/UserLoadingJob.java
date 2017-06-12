package com.tecacet.movielens.easybatch;

import java.io.File;
import java.io.IOException;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.easybathc.converter.GenderTypeConverter;
import com.tecacet.movielens.easybathc.converter.OccupationTypeConverter;
import com.tecacet.movielens.model.User;

@Component
public class UserLoadingJob {

    private static final String USER_FILENAME = "../ml-100k/u.user";

    private final UserLoadingProcessor userLoadingProcessor;

    @Autowired
    public UserLoadingJob(UserLoadingProcessor userLoadingProcessor) {
        super();
        this.userLoadingProcessor = userLoadingProcessor;
    }

    public JobReport readUsers() throws IOException {
        Job job = buildJob();
        return JobExecutor.execute(job);
    }

    private Job buildJob() throws IOException {
        DelimitedRecordMapper recordMapper = new DelimitedRecordMapper(User.class, "id", "age", "gender", "occupation", "zipCode");
        recordMapper.setDelimiter("|");
        recordMapper.registerTypeConverter(new GenderTypeConverter());
        recordMapper.registerTypeConverter(new OccupationTypeConverter());
        Job job = JobBuilder.aNewJob().jmxMode(true).reader(new FlatFileRecordReader(new File(USER_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<User>()).processor(userLoadingProcessor).build();
        return job;
    }

  
}
