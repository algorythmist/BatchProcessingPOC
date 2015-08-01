package com.tecacet.movielens.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class UserRating {

    @Id
    private String id;
    
    private long userId;
    private long itemId;
    @Min(1)@Max(5)
    private int rating;
    private long timestamp;

    public UserRating() {
        super();
    }

    public UserRating(long userId, long itemId, int rating, long timestamp) {
        this();
        this.userId = userId;
        this.itemId = itemId;
        this.rating = rating;
        this.timestamp = timestamp;
        this.id = String.format("%d,%d", userId, itemId);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    

}
