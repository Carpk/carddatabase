package com.thekleinbottle.carddatabase.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thekleinbottle.carddatabase.domain.Car;
import com.thekleinbottle.carddatabase.domain.CarRepository;


@RestController
public class CarController {
    private final CarRepository repository;
    
    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cars")
    public Iterable<Car> getCars() {
        return repository.findAll();
    }
    
}
