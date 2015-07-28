package com.tecacet.easybatch;

import java.io.File;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;

import com.tecacet.movielens.model.User;

public class UserLoadingJob {

    private static final String USER_FILENAME = "../ml-100k/u.user";
    
    public static void main(String[] args) throws Exception {
        DelimitedRecordMapper<User> recordMapper = new DelimitedRecordMapper<User>(User.class, new String[]{"id","age","gender","occupation","zipCode"});
        recordMapper.setDelimiter("|");
        Engine engine = new EngineBuilder().reader(new FlatFileRecordReader(new File(USER_FILENAME)))
                .mapper(recordMapper)
                .validator(new BeanValidationRecordValidator<User>())
                .build();
        Report report = engine.call();
        System.out.println(report);
        
    }
}
