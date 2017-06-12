package com.tecacet.movielens.easybatch;

import java.io.File;
import java.io.IOException;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.mapper.RecordMappingException;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecacet.movielens.easybathc.converter.LocalDateTypeConverter;
import com.tecacet.movielens.model.Movie;

@Component
public class MovieLoadingJob {

	private static final String MOVIE_FILENAME = "../ml-100k/u.item";

	private final MovieLoadingProcessor movieProcessor;

	class DelegatingRecordMapper implements RecordMapper<StringRecord, Record<Movie>> {

		private DelimitedRecordMapper recordMapper = new DelimitedRecordMapper(Movie.class,
				new Integer[] { 0, 1, 2, 3, 4 },
				new String[] { "id", "title", "releaseDate", "videoReleaseDate", "IMDBurl" });

		public DelegatingRecordMapper() {
			recordMapper.setDelimiter("|");
			recordMapper.registerTypeConverter(new LocalDateTypeConverter());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Record<Movie> processRecord(StringRecord record) throws RecordMappingException {
			Record<Movie> movieRecord = recordMapper.processRecord(record);
			String[] tokens = record.getPayload().split("\\|");
			for (int i = 5; i < 24; i++) {
				int index = Integer.parseInt(tokens[i]);
				if (index == 1) {
					movieRecord.getPayload().addGenre(i - 5);
				}
			}
			return movieRecord;
		}
	}

	@Autowired
	public MovieLoadingJob(MovieLoadingProcessor movieProcessor) {
		super();
		this.movieProcessor = movieProcessor;
	}

	public JobReport readMovies() throws IOException {
		Job job = buildJob();
		return JobExecutor.execute(job);
	}

	private Job buildJob() throws IOException {
		DelegatingRecordMapper recordMapper = new DelegatingRecordMapper();
		Job job = JobBuilder.aNewJob().jmxMode(true).reader(new FlatFileRecordReader(new File(MOVIE_FILENAME)))
				.mapper(recordMapper).validator(new BeanValidationRecordValidator<Movie>()).processor(movieProcessor)
				.build();
		return job;
	}

}
