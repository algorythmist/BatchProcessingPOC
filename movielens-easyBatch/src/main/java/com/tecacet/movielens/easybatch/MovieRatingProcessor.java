package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.model.UserRating;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;

import java.util.HashMap;
import java.util.Map;

public class MovieRatingProcessor implements RecordProcessor<Record<UserRating>, Record<UserRating>> {

    private final Map<Long, MovieMetrics> metrics = new HashMap<>();

    @Override
    public Record<UserRating> processRecord(Record<UserRating> record) {
        UserRating rating = record.getPayload();
        MovieMetrics movieMetrics = metrics.get(rating.getItemId());
        if (movieMetrics == null) {
            movieMetrics = new MovieMetrics();
            metrics.put(rating.getItemId(), movieMetrics);
        }
        movieMetrics.addRating(rating);
        return record;
    }

    //TODO
    //	@Override
    //	public Map<Long, MovieMetrics> getComputationResult() {
    //		return metrics;
    //	}

}
