package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.model.Movie;
import com.tecacet.movielens.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class MovieMetricsFileWriter {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieMetricsFileWriter(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    public File writeMetrics(Map<Long, MovieMetrics> metrics) throws IOException {
        List<Movie> movies = movieRepository.findAll();
        File file = new File("movie_metrics.csv");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(getHeader());
        for (Movie m : movies) {
            MovieMetrics movieMetrics = metrics.get(m.getId());
            fileWriter.write(toLine(m, movieMetrics));
        }
        fileWriter.close();
        return file;
    }

    private String getHeader() {
        return String.join(",", "Title", "Release", "Total Ratings", "Like", "Mean rating") + "\n";
    }

    private String toLine(Movie movie, MovieMetrics metrics) {
        return String.format("\"%s\",\"%s\", %d, %d, %.2f\n", movie.getTitle(), movie.getReleaseDate(),
                metrics.getCount(), metrics.getLikes(), metrics.getMean());
    }
}
