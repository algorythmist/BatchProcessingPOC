package com.tecacet.movielens.easybatch;

import java.util.HashMap;
import java.util.Map;

import org.easybatch.core.api.ComputationalRecordProcessor;
import org.easybatch.core.api.RecordProcessingException;

import com.tecacet.movielens.model.Occupation;
import com.tecacet.movielens.model.User;

public class UserClassifyingProcessor implements ComputationalRecordProcessor<User, User, Map<Occupation,Integer>>{

    private Map<Occupation,Integer> occupationCounts = new HashMap<>();
    
    @Override
    public User processRecord(User user) throws RecordProcessingException {
        
        Occupation occupation = user.getOccupation();
        Integer count = occupationCounts.get(occupation);
        if (count == null) {
            occupationCounts.put(occupation, 1);
        } else {
            occupationCounts.put(occupation, count+1);
        }
        return user;
    }

    @Override
    public Map<Occupation, Integer> getComputationResult() {
        return occupationCounts;
    }

}
