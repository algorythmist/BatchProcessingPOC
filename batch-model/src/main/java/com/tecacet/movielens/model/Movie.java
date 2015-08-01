package com.tecacet.movielens.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class Movie {

    @Id
    private long id;
    
    @NotEmpty
    private String title;
    
    @NotNull
    private LocalDate releaseDate;
    
    private LocalDate videoReleaseDate;
    
    private String IMDBurl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDate getVideoReleaseDate() {
        return videoReleaseDate;
    }

    public void setVideoReleaseDate(LocalDate videoReleaseDate) {
        this.videoReleaseDate = videoReleaseDate;
    }

    public String getIMDBurl() {
        return IMDBurl;
    }

    public void setIMDBurl(String iMDBurl) {
        IMDBurl = iMDBurl;
    }

}
