package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.model.User;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class JsonTransformingProcessor implements RecordProcessor<Record<User>, StringRecord> {

    @Override
    public StringRecord processRecord(Record<User> record) throws IOException {

        User user = record.getPayload();
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", user.getId())
                .field("age", user.getAge()).field("gender", user.getGender()).endObject();
        return new StringRecord(record.getHeader(), builder.string());

    }

}
