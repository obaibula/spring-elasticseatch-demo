package com.example.demo.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.demo.document.Vehicle;
import com.example.demo.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Vehicle vehicle) {
        vehicleService.index(vehicle);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable String id) {
        var vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(vehicle);
    }

}
