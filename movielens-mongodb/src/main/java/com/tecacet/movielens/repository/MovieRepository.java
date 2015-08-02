package com.tecacet.movielens.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecacet.movielens.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, Long> {

}
