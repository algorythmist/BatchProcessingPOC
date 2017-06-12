package com.tecacet.movielens.easybatch;

import static org.junit.Assert.*;

import java.util.Map;

import javax.annotation.Resource;

import org.easybatch.core.job.JobReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tecacet.movielens.MongoConfig;
import com.tecacet.movielens.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class, MongoConfig.class })
public class ComputeMovieMetricsJobTest {

	@Resource
	private ComputeMovieMetricsJob computeMovieMetricsJob;
	
	@Resource
	private RatingLoadingJob ratingLoadingJob;

	@Test
	public void testComputeMetrics() throws Exception {
		JobReport jobReport = ratingLoadingJob.readRatings();
		System.out.println(jobReport); //TODO
		Map<Long, MovieMetrics> metrics = computeMovieMetricsJob.computeMetrics();
		assertEquals(1682, metrics.size());
	}

}
