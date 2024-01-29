package com.example.demo.search;

import java.util.List;

public record SearchRequestDto(List<String> fields, String searchTerm) {
}
