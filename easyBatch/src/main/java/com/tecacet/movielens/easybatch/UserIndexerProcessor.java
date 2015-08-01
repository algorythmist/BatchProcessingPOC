package com.tecacet.easybatch;

import org.easybatch.core.api.RecordProcessor;
import org.elasticsearch.client.Client;

public class UserIndexerProcessor implements RecordProcessor<String, String> {

    /** Elastic search client */
    private Client client;

    public UserIndexerProcessor(Client client) {
        this.client = client;
    }

    @Override
    public String processRecord(String userString) {
        //index the tweet in the twitter index
        client.prepareIndex("movielens", "user").setSource(userString).execute().actionGet();
        return userString;
    }

}