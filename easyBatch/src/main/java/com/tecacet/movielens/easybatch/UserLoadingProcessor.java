package com.tecacet.movielens.easybatch;



import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.model.User;
import com.tecacet.movielens.repository.UserRepository;

@Component
public class UserLoadingProcessor implements RecordProcessor<User, User> {

    private final UserRepository userRepository;

    @Autowired
    public UserLoadingProcessor(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User processRecord(User user) throws RecordProcessingException {
        userRepository.save(user);
        return user;
    }

}
