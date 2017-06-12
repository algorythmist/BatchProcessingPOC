package com.tecacet.movielens.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class Movie {

    public static final String[] GENRES = {
            "unknown",
            "Action",
            "Adventure",
            "Animation",
            "Children's",
            "Comedy",
            "Crime",
            "Documentary",
            "Drama",
            "Fantasy",
            "Film-Noir",
            "Horror",
            "Musical",
            "Mystery",
            "Romance",
            "Sci-Fi",
            "Thriller",
            "War",
            "Western"

    };
    
    @Id
    private long id;
    
    @NotEmpty
    private String title;
    
    private LocalDate releaseDate;
    
    private LocalDate videoReleaseDate;
    
    private String IMDBurl;
    
    private Set<Integer> genres = new LinkedHashSet<>();

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

    public Set<Integer> getGenres() {
        return genres;
    }

    public void addGenre(int genreIndex) {
        this.genres.add(genreIndex);
    }

    public void setGenres(Set<Integer> genres) {
        this.genres = genres;
    }
    
    @Override
    public String toString() {
    	return title;
    }

}
