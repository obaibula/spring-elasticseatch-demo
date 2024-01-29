package com.example.demo.util;

import static co.elastic.clients.elasticsearch._types.query_dsl.Operator.And;
import static co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType.CrossFields;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.example.demo.search.SearchRequestDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class SearchUtil {

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDto dto) {
        return new SearchRequest.Builder()
            .postFilter(getQuery(dto))
            .index(indexName)
            .build();
    }

    public static Query getQuery(SearchRequestDto dto) {
        if (isNull(dto)) {
            return null;
        }

        var fields = dto.fields();
        if (isNull(fields) || fields.isEmpty()) {
            return null;
        }

        return QueryBuilders.multiMatch(fn -> fn
            .fields(fields)
            .query(dto.searchTerm())
            .type(CrossFields)
            .operator(And));

    }

}
