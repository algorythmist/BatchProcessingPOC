package com.tecacet.movielens.easybatch;

import org.easybatch.core.api.event.step.RecordProcessorEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingEventListener implements RecordProcessorEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object beforeRecordProcessing(Object record) {
        logger.info("About to process " + record);
        return record;
    }

    @Override
    public void afterRecordProcessing(Object record, Object result) {
        logger.info("Obtained result " + result);
    }

    @Override
    public void onRecordProcessingException(Object record, Throwable throwable) {
        logger.error("Oi oi oi: "+throwable.getMessage());
    }

}
