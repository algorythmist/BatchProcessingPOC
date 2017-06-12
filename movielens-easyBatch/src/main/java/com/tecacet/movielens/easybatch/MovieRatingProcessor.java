package com.tecacet.movielens.easybatch;

import java.util.HashMap;
import java.util.Map;

import org.easybatch.core.processor.ComputationalRecordProcessor;
import org.easybatch.core.processor.RecordProcessingException;
import org.easybatch.core.record.Record;

import com.tecacet.movielens.model.UserRating;

public class MovieRatingProcessor
		implements ComputationalRecordProcessor<Record<UserRating>, Record<UserRating>, Map<Long, MovieMetrics>> {

	private Map<Long, MovieMetrics> metrics = new HashMap<>();

	@Override
	public Record<UserRating> processRecord(Record<UserRating> record) throws RecordProcessingException {
		UserRating rating = record.getPayload();
		MovieMetrics movieMetrics = metrics.get(rating.getItemId());
		if (movieMetrics == null) {
			movieMetrics = new MovieMetrics();
			metrics.put(rating.getItemId(), movieMetrics);
		}
		movieMetrics.addRating(rating);
		return record;
	}

	@Override
	public Map<Long, MovieMetrics> getComputationResult() {
		return metrics;
	}

}
