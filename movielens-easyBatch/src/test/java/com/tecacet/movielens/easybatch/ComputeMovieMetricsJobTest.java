package com.tecacet.movielens.easybatch;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
	private MovieLoadingJob movieLoadingJob;
	
	@Resource
	private RatingLoadingJob ratingLoadingJob;
	
	@Resource
	private MovieMetricsFileWriter fileWriter;

	@Test
	public void testComputeMetrics() throws Exception {
		movieLoadingJob.readMovies();
		JobReport jobReport = ratingLoadingJob.readRatings();
		System.out.println(jobReport); //TODO
		Map<Long, MovieMetrics> metrics = computeMovieMetricsJob.computeMetrics();
		assertEquals(1682, metrics.size());
		File file = fileWriter.writeMetrics(metrics);
		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
		assertEquals(1683, lines.size());
		file.delete();	
	}

}
