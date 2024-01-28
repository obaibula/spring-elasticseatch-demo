package com.example.demo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.demo.helper.Indices;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexService {

    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);

    private final ElasticsearchClient elasticsearchClient;

    @PostConstruct
    public void tryToCreateIndices() {
        try (var settingJson = new ClassPathResource("static/es-settings.json").getInputStream()) {
            for (var indexName : INDICES_TO_CREATE) {
                var exists = elasticsearchClient.indices()
                    .exists(request -> request.index(indexName))
                    .value();
                if (!exists) {
                    createIndex(indexName, settingJson);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private void createIndex(String indexName, InputStream settingJson) throws IOException {
        var mappingsJsonPath = "static/mappings/" + indexName + ".json";
        try (var mappingJson = new ClassPathResource(mappingsJsonPath).getInputStream()) {
            elasticsearchClient.indices().create(request -> request
                .index(indexName)
                .settings(settings -> settings.withJson(settingJson))
                .mappings(mapping -> mapping.withJson(mappingJson)));
        }
    }

}
