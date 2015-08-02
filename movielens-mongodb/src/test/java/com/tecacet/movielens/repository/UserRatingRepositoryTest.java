package com.tecacet.movielens.repository;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.model.UserRating;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class UserRatingRepositoryTest {

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Test
    public void test() {
        UserRating userRating1 = new UserRating(10L, 10L, 4, new Date().getTime());
        userRatingRepository.save(userRating1);

        UserRating userRating2 = new UserRating(5L, 10L, 2, new Date().getTime());
        userRatingRepository.save(userRating2);

        UserRating found = userRatingRepository.findByUserIdAndItemId(10L, 10L);
        assertNotNull(found);

        userRatingRepository.delete(userRating1);
        userRatingRepository.delete(userRating2);
    }

}
