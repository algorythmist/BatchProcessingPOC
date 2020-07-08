package com.tecacet.movielens.easybatch;

import com.tecacet.movielens.elastic.ElasticServer;
import com.tecacet.movielens.model.User;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import java.io.File;

public class UserIndexingJob {

    private static final String USER_FILENAME = "../ml-100k/u.user";

    private final ElasticServer elasticServer = new ElasticServer();
    private final JobExecutor jobExecutor = new JobExecutor();

    public JobReport indexUsers() throws Exception {
        // start embedded elastic search node
        Node node = elasticServer.startEmbeddedNode();
        Client client = node.client();

        DelimitedRecordMapper recordMapper = new DelimitedRecordMapper(User.class, "id", "age", "gender", "occupation",
                "zipCode");
        recordMapper.setDelimiter("|");
        Job job = new JobBuilder().enableJmx(true).reader(new FlatFileRecordReader(new File(USER_FILENAME)))
                .mapper(recordMapper).validator(new BeanValidationRecordValidator())
                .processor(new MovieRatingProcessor()).processor(new JsonTransformingProcessor())
                .processor(new UserIndexerProcessor(client)).pipelineListener(new LoggingEventListener()).build();
        JobReport report = jobExecutor.execute(job);

        // shutdown elastic search node
        elasticServer.stopEmbeddedNode(node);
        return report;
    }

    public static void main(String[] args) throws Exception {
        new UserIndexingJob().indexUsers();

    }
}
