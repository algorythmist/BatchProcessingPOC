package com.tecacet.movielens.easybatch;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.annotation.Resource;

import org.easybatch.core.job.JobReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;
import com.tecacet.movielens.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class, MongoConfig.class })
public class UserLoadingJobTest {

	@Resource
	private UserLoadingJob userLoadingJob;
	
	@Resource
	private UserRepository userRepository;

	@Test
	public void test() throws IOException {
		JobReport jobReport = userLoadingJob.readUsers();
		System.out.println(jobReport);
		assertEquals(943, userRepository.findAll());
		userRepository.deleteAll();
	}

}
