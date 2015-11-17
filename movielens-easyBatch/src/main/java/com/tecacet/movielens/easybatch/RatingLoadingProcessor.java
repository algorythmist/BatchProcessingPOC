package com.tecacet.movielens.easybatch;

import org.easybatch.core.processor.RecordProcessingException;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.UserRatingRepository;

@Component
public class RatingLoadingProcessor implements RecordProcessor<Record<UserRating>, Record<UserRating>> {

    private final UserRatingRepository userRatingRepository;

    @Autowired
    public RatingLoadingProcessor(UserRatingRepository userRatingRepository) {
        super();
        this.userRatingRepository = userRatingRepository;
    }

    @Override
    public Record<UserRating> processRecord(Record<UserRating> record) throws RecordProcessingException {
        userRatingRepository.save(record.getPayload());
        return record;
    }

}
