package com.example.demo.controller;

import com.example.demo.model.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    /*@Value("${spring.elasticsearch.uris}")
    public List<String> elasticSearchUris;*/

    @GetMapping
    public List<Customer> get() {
        return List.of(new Customer("Oleh", "Baibula"));
    }

}
