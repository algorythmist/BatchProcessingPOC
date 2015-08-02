package com.tecacet.movielens.easybatch;

import java.util.HashMap;
import java.util.Map;

import org.easybatch.core.api.ComputationalRecordProcessor;
import org.easybatch.core.api.RecordProcessingException;

import com.tecacet.movielens.model.UserRating;

public class MovieRatingProcessor implements ComputationalRecordProcessor<UserRating, UserRating, Map<Long,MovieMetrics>>{
    
    private Map<Long,MovieMetrics> metrics = new HashMap<>();
    
    @Override
    public UserRating processRecord(UserRating rating) throws RecordProcessingException {
        
        
       MovieMetrics movieMetrics = metrics.get(rating.getItemId());
        if (movieMetrics == null) {
            movieMetrics = new MovieMetrics();
            metrics.put(rating.getItemId(), movieMetrics);
        }
        movieMetrics.addRating(rating);
        return rating;
    }

    @Override
    public Map<Long,MovieMetrics> getComputationResult() {
        return metrics;
    }

}
