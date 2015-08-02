package com.tecacet.movielens.easybatch;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.UserRatingRepository;

@Component
public class RatingLoadingProcessor implements RecordProcessor<UserRating, UserRating> {

    private final UserRatingRepository userRatingRepository;

    @Autowired
    public RatingLoadingProcessor(UserRatingRepository userRatingRepository) {
        super();
        this.userRatingRepository = userRatingRepository;
    }

    @Override
    public UserRating processRecord(UserRating rating) throws RecordProcessingException {
        return userRatingRepository.save(rating);
    }

}
