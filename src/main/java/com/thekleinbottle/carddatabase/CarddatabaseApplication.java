package com.thekleinbottle.carddatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thekleinbottle.carddatabase.domain.Car;
import com.thekleinbottle.carddatabase.domain.CarRepository;

@SpringBootApplication
public class CarddatabaseApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CarddatabaseApplication.class);
	private final CarRepository repository;

	public CarddatabaseApplication(CarRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CarddatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2023, 59000));
		repository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000));
		repository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000));
		
		for (Car car : repository.findAll()) {
			logger.info("brand: {}, model: {}",
			car.getBrand(), car.getModel());
		}
	}

}
