package com.tecacet.movielens.easybatch;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.StringRecord;
import org.elasticsearch.client.Client;

public class UserIndexerProcessor implements RecordProcessor<StringRecord, StringRecord> {

	/** Elastic search client */
	private Client client;

	public UserIndexerProcessor(Client client) {
		this.client = client;
	}

	@Override
	public StringRecord processRecord(StringRecord record) {
		// index the user in the movielens index
		client.prepareIndex("movielens", "user").setSource(record.getPayload()).execute().actionGet();
		return record;
	}

}
