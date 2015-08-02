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

    public void readUsers() throws Exception {

        Engine engine = buildEngine();
        Report report = engine.call();

    }

    private Engine buildEngine() throws FileNotFoundException {
        DelimitedRecordMapper<User> recordMapper = new DelimitedRecordMapper<User>(User.class, new String[] { "id", "age", "gender", "occupation",
                "zipCode" });
        recordMapper.setDelimiter("|");
        recordMapper.registerTypeConverter(new GenderTypeConverter());
        recordMapper.registerTypeConverter(new OccupationTypeConverter());
        Engine engine = new EngineBuilder().enableJMX(true).reader(new FlatFileRecordReader(new File(USER_FILENAME))).mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<User>()).processor(userLoadingProcessor).build();
        return engine;
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class, SpringConfig.class);
        UserLoadingJob job = context.getBean(UserLoadingJob.class);
        job.readUsers();

    }
}
