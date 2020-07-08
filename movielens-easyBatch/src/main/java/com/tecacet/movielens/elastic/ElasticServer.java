package com.tecacet.movielens.elastic;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.io.File;
import java.io.IOException;

public class ElasticServer {
    private static final String ES_DATA_DIRECTORY = "./es-data";

    public Node startEmbeddedNode() {
        ImmutableSettings.Builder elasticSearchSettings = ImmutableSettings.settingsBuilder().put("http.enabled", "false")
                .put("path.data", ES_DATA_DIRECTORY);

        return NodeBuilder.nodeBuilder().local(true).settings(elasticSearchSettings.build()).node();
    }

    public void stopEmbeddedNode(Node node) throws IOException {
        node.close();
        deleteElasticSearchDataDirectory();
    }

    private void deleteElasticSearchDataDirectory() {
        new File(ES_DATA_DIRECTORY).delete();
    }


}
