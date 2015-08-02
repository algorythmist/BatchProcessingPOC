package com.tecacet.movielens.easybatch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.Movie;
import com.tecacet.movielens.repository.MovieRepository;

@Component
public class MovieMetricsFileWriter {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieMetricsFileWriter(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    public void writeMetrics(Map<Long, MovieMetrics> metrics) throws IOException {
        List<Movie> movies = movieRepository.findAll();
        FileWriter fileWriter = new FileWriter("movie_metrics.csv");
        fileWriter.write(getHeader());
        for (Movie m : movies) {
            MovieMetrics movieMetrics = metrics.get(m.getId());
            fileWriter.write(toLine(m, movieMetrics));
        }
        fileWriter.close();
    }

    private String getHeader() {
        return String.join(",", "Title", "Release", "Total Ratings", "Like", "Mean rating")+"\n";
    }

    private String toLine(Movie movie, MovieMetrics metrics) {
        
        return String.format("\"%s\",\"%s\", %d, %d, %.2f\n", movie.getTitle(), movie.getReleaseDate().toString(),metrics.getCount(),
                metrics.getLikes(), metrics.getMean());
    }
}
