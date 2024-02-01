package com.example.demo.search;

import co.elastic.clients.elasticsearch._types.SortOrder;
import java.util.List;

public record SearchRequestDto(

    List<String> fields,

    String searchTerm,

    String sortBy,

    SortOrder order) {

}
