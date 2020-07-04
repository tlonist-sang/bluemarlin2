package com.project.bluemarlin2.bluemarlin2.config;


import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig {
    @Value(value = "${elasticsearch.host}")
    String elasticSearchHost;

    @Bean
    public Client client() throws Exception {

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticSearchHost), 9300));
    }
}
