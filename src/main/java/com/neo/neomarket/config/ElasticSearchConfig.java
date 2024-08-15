package com.neo.neomarket.config;

import com.neo.neomarket.repository.elasticsearch.AuctionLogRepository;
import com.neo.neomarket.repository.elasticsearch.UserLogRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {AuctionLogRepository.class, UserLogRepository.class})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()

                .connectedTo("elasticsearch:9200")

                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}