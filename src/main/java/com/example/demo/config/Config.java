package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories("com.example.demo.repository")
public class Config extends ElasticsearchConfiguration {

    @NonNull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.localhost();
    }

}
