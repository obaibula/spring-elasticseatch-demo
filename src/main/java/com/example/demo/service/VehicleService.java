package com.example.demo.service;

import static com.example.demo.helper.Indices.VEHICLE_INDEX;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import com.example.demo.document.Vehicle;
import java.io.IOException;
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
