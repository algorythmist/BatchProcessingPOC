package com.tecacet.movielens.springbatch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.UserRating;

@Component
@StepScope
public class RatingItemReader extends FlatFileItemReader<UserRating> {

	public RatingItemReader() {
//		Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();
//		customEditors.put(Gender.class, new GenderPropertyEditor());
//		customEditors.put(Occupation.class, new OccupationPropertyEditor());

		RegexLineTokenizer lineTokenizer = new RegexLineTokenizer() {
			{
				setRegex("(.*)\\s+(.*)\\s+(.*)\\s+(.*)\\b");
				setNames(new String[] { "usedId", "itemId", "rating", "timestamp"});
			}
		};
		setResource(new FileSystemResource("../ml-100k/u.data"));
		setLineMapper(new DefaultLineMapper<UserRating>() {
			{
				
				setLineTokenizer(lineTokenizer);
				setFieldSetMapper(new BeanWrapperFieldSetMapper<UserRating>() {
					{
						setTargetType(UserRating.class);
					}
				});

			}
		});
	}
}
