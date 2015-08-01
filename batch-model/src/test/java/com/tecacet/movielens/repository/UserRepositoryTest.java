package com.tecacet.movielens.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.model.Gender;
import com.tecacet.movielens.model.Occupation;
import com.tecacet.movielens.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        User user = new User();
        user.setAge(10);
        user.setGender(Gender.MALE);
        user.setOccupation(Occupation.doctor);
        user.setZipCode("12345");
        userRepository.save(user);
        assertEquals(1, userRepository.count());

        User found = userRepository.findOne(user.getId());
        assertEquals(10, found.getAge());
        assertEquals(Gender.MALE, found.getGender());
        assertEquals(Occupation.doctor, found.getOccupation());
        
        userRepository.delete(user);
        assertEquals(0, userRepository.count());
    }
}
