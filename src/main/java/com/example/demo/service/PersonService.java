package com.example.demo.service;

import com.example.demo.document.Person;
import com.example.demo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void save(Person person) {
        personRepository.save(person);
    }

    public Person findById(String id) {
        return personRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Person with id %s has not been found".formatted(id)));
    }

}
