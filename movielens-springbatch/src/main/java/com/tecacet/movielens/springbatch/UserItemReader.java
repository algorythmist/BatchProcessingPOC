package com.tecacet.movielens.springbatch;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.Gender;
import com.tecacet.movielens.model.Occupation;
import com.tecacet.movielens.model.User;
import com.tecacet.movielens.springbatch.converter.GenderPropertyEditor;
import com.tecacet.movielens.springbatch.converter.OccupationPropertyEditor;

@Component
@StepScope
public class UserItemReader extends FlatFileItemReader<User> {

	public UserItemReader() {
		Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();
		customEditors.put(Gender.class, new GenderPropertyEditor());
		customEditors.put(Occupation.class, new OccupationPropertyEditor());
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("|") {
			{
				setNames(new String[] { "id", "age", "gender", "occupation",
						"zipCode" });
			}
		};
		setResource(new FileSystemResource("../ml-100k/u.user"));
		setLineMapper(new DefaultLineMapper<User>() {
			{
				
				setLineTokenizer(lineTokenizer);
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
						setCustomEditors(customEditors);
					}
				});

			}
		});
	}
}
