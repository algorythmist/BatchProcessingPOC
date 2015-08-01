package com.tecacet.movielens;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "movielens";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1");
	}

	@Bean(name = "validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();

    }

	
}
