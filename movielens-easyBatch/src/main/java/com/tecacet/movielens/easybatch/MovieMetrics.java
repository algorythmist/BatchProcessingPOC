package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.model.UserRating;

public class MovieMetrics {

    private long totalRating;
    private long count;
    private long likes;

    public void addRating(UserRating rating) {
        count++;
        totalRating += rating.getRating();
        if (rating.getRating() >= 4) {
            likes++;
        }
    }

    public long getTotalRating() {
        return totalRating;
    }

    public long getCount() {
        return count;
    }

    public long getLikes() {
        return likes;
    }

    public double getMean() {
        return (double) totalRating / (double) count;
    }
}
