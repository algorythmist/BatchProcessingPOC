package com.tecacet.movielens.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecacet.movielens.model.User;

public interface UserRepository extends MongoRepository<User, Long>{

}
