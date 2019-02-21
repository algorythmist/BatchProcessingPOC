package com.tecacet.movielens.easybatch;

import java.io.File;
import java.io.IOException;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.mapper.RecordMapper;
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
    private final JobExecutor jobExecutor = new JobExecutor();

    class DelegatingRecordMapper implements RecordMapper<StringRecord, Record<Movie>> {

        private DelimitedRecordMapper<Movie> recordMapper = new DelimitedRecordMapper<>(Movie.class,
                new Integer[]{0, 1, 2, 3, 4},
                new String[]{"id", "title", "releaseDate", "videoReleaseDate", "IMDBurl"});

        public DelegatingRecordMapper() {
            recordMapper.setDelimiter("|");
            recordMapper.registerTypeConverter(new LocalDateTypeConverter());
        }

        @Override
        //TODO: java.lang.Exception: record length (7 fields) not equal to expected length of 24 fields
        public Record<Movie> processRecord(StringRecord record) throws Exception {
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
        return jobExecutor.execute(job);
    }

    private Job buildJob() {
        DelegatingRecordMapper recordMapper = new DelegatingRecordMapper();
        FlatFileRecordReader flatFileRecordReader = new FlatFileRecordReader(new File(MOVIE_FILENAME));
        return JobBuilder.aNewJob().enableJmx(true).reader(flatFileRecordReader)
                .mapper(recordMapper).validator(new BeanValidationRecordValidator()).processor(movieProcessor).build();
    }

}
