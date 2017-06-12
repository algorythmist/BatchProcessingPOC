package com.tecacet.movielens.springbatch;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PrintItemWriter<T> implements ItemWriter<T>{

	@Override
	public void write(List<? extends T> list) throws Exception {
		list.forEach(item -> System.out.println(item));
	}

}
