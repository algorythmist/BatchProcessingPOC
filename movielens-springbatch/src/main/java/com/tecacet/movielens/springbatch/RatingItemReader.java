package com.tecacet.movielens.springbatch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.UserRating;

@Component
@StepScope
public class RatingItemReader extends FlatFileItemReader<UserRating> {

	private static final String RATINGS_FILENAME = "../ml-100k/u.data";
	private static final String[] FIELDS = new String[] { "usedId", "itemId", "rating", "timestamp" };

	public RatingItemReader() {
		RegexLineTokenizer lineTokenizer = new RegexLineTokenizer();
		lineTokenizer.setRegex("(.*)\\s+(.*)\\s+(.*)\\s+(.*)\\b");
		lineTokenizer.setNames(FIELDS);
		setResource(new FileSystemResource(RATINGS_FILENAME));
		DefaultLineMapper<UserRating> lineMapper = new DefaultLineMapper<UserRating>();
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<UserRating> fieldSetMapper = new BeanWrapperFieldSetMapper<UserRating>();
		fieldSetMapper.setTargetType(UserRating.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		setLineMapper(lineMapper);
	}
}
