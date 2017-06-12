package com.tecacet.movielens.easybatch;

import org.easybatch.core.listener.PipelineListener;
import org.easybatch.core.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingEventListener implements PipelineListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Record beforeRecordProcessing(Record record) {
		logger.info("About to process " + record);
		return record;
	}

	@Override
	public void afterRecordProcessing(Record inputRecord, Record outputRecord) {
		logger.info("Obtained result " + outputRecord);
	}

	@Override
	public void onRecordProcessingException(Record record, Throwable throwable) {
		logger.error("Oi oi oi: " + throwable.getMessage());
	}

}
