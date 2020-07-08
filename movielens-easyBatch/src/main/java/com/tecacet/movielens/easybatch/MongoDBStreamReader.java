package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.model.UserRating;
import com.tecacet.movielens.repository.UserRatingRepository;

import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.stream.Stream;

//TODO use stream reader
public class MongoDBStreamReader implements RecordReader {

    private final UserRatingRepository repository;

    @Autowired
    public MongoDBStreamReader(UserRatingRepository repository) {
        super();
        this.repository = repository;
    }

    private Stream<UserRating> stream;
    private Iterator<UserRating> iterator;
    private long count;

    private long current = 0;

    @Override
    public void open() {
        stream = repository.findAllAndStream();
        count = repository.count();
        iterator = stream.iterator();
    }

    @Override
    public Record<UserRating> readRecord() {
        if (iterator.hasNext()) {
            UserRating rating = iterator.next();
            return new GenericRecord<>(new Header(++current, getDataSourceName(), new Date()), rating);
        }
        return null;
    }

    // @Override
    // public Long getTotalRecords() {
    // return count;
    // }
    //

    private String getDataSourceName() {
        // TODO
        return "mongoDB collection";
    }

    @Override
    public void close() {
        stream.close();
    }

}
