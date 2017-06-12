package com.tecacet.movielens.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tecacet.movielens.model.UserRating;

@Repository
public interface UserRatingRepository extends MongoRepository<UserRating, Long>{

    UserRating findByUserIdAndItemId(long userId, long movieId);
    
    @Query("{}")
    Stream<UserRating> findAllAndStream();

  
}
