package com.example.demo.service;

import static com.example.demo.helper.Indices.VEHICLE_INDEX;
import static java.util.Objects.isNull;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.demo.document.Vehicle;
import com.example.demo.search.SearchRequestDto;
import com.example.demo.util.SearchUtil;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final ElasticsearchClient elasticsearchClient;

    public List<Vehicle> getAll(SearchRequestDto dto) {
        var request = SearchUtil.buildSearchRequest(VEHICLE_INDEX, dto);

        return getVehicles(request);
    }

    public List<Vehicle> getAllCreatedSince(Date date) {
        var request = SearchUtil.buildSearchRequest(
            VEHICLE_INDEX,
            "created",
            date);

        return getVehicles(request);
    }

    private List<Vehicle> getVehicles(SearchRequest request) {
        if (isNull(request)) {
            return List.of();
        }

        try {
            var response = elasticsearchClient.search(request, Vehicle.class);
            return response.hits()
                .hits()
                .stream()
                .map(Hit::source)
                .toList();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return List.of();
        }
    }

    public void index(Vehicle vehicle) {
        try {
            elasticsearchClient.index(request -> request.index(VEHICLE_INDEX)
                .id(vehicle.getId())
                .document(vehicle));

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Vehicle findById(String vehicleId) {
        try {
            var getRequest = GetRequest.of(b -> b.index(VEHICLE_INDEX).id(vehicleId));
            return Optional.ofNullable(elasticsearchClient.get(getRequest, Vehicle.class).source())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id - " + vehicleId));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

}
