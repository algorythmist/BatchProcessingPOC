package com.tecacet.movielens.easybatch;



import org.easybatch.core.processor.RecordProcessingException;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.User;
import com.tecacet.movielens.repository.UserRepository;

@Component
public class UserLoadingProcessor implements RecordProcessor<Record<User>, Record<User>> {

    private final UserRepository userRepository;

    @Autowired
    public UserLoadingProcessor(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public Record<User> processRecord(Record<User> record) throws RecordProcessingException {
        userRepository.save(record.getPayload());
        return record;
    }

}
