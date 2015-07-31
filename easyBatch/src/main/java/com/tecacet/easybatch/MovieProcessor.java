package com.tecacet.easybatch;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.tecacet.movielens.model.Movie;

public class MovieProcessor implements RecordProcessor<Movie, Movie> {

    private final MongoClient mongoClient = new MongoClient();

    @Override
    public Movie processRecord(Movie movie) throws RecordProcessingException {
        //MongoDatabase database = mongoClient.getDatabase( "movielens" );
        DB database = mongoClient.getDB("movielens");
        Jongo jongo = new Jongo(database);
        MongoCollection friends = jongo.getCollection("movies");
        friends.save(movie);
        return movie;
    }

}
