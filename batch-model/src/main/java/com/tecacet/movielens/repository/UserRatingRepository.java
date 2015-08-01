package com.tecacet.movielens.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecacet.movielens.model.UserRating;

public interface UserRatingRepository extends MongoRepository<UserRating, Long>{

    UserRating findByUserIdAndItemId(long userId, long movieId);
}
