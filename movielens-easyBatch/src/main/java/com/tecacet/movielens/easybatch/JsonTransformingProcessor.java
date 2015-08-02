package com.tecacet.movielens.easybatch;

import java.io.IOException;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.tecacet.movielens.model.User;

public class JsonTransformingProcessor implements RecordProcessor<User, String> {

    @Override
    public String processRecord(User user) throws RecordProcessingException {

        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder().startObject().field("id", user.getId()).field("age", user.getAge())
                    .field("gender", user.getGender()).endObject();
            return builder.string();
        } catch (IOException e) {
            throw new RecordProcessingException("Failed to map record", e);
        }

    }

}
