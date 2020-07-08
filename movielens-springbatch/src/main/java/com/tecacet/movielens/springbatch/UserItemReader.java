package com.tecacet.movielens.springbatch;

import com.tecacet.movielens.model.Gender;
import com.tecacet.movielens.model.Occupation;
import com.tecacet.movielens.model.User;
import com.tecacet.movielens.springbatch.converter.GenderPropertyEditor;
import com.tecacet.movielens.springbatch.converter.OccupationPropertyEditor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

@Component
@StepScope
public class UserItemReader extends FlatFileItemReader<User> {

    private static final String USER_FILENAME = "../ml-100k/u.user";
    private static final String[] FIELDS = new String[] {"id", "age", "gender", "occupation", "zipCode"};

    public UserItemReader() {
        Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();
        customEditors.put(Gender.class, new GenderPropertyEditor());
        customEditors.put(Occupation.class, new OccupationPropertyEditor());

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("|");
        lineTokenizer.setNames(FIELDS);

        setResource(new FileSystemResource(USER_FILENAME));
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        fieldSetMapper.setCustomEditors(customEditors);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }
}
