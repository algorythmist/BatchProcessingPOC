package com.tecacet.movielens.model;

import java.time.LocalDate;

//movie id | movie title | release date | video release date |
//IMDb URL | unknown | Action | Adventure | Animation |
//Children's | Comedy | Crime | Documentary | Drama | Fantasy |
//Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi |
//Thriller | War | Western |
public class Movie {

    private long id;
    private String title;
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
