package com.tecacet.movielens.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tecacet.movielens.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Long> {

}
