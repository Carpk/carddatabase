package com.thekleinbottle.carddatabase;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thekleinbottle.carddatabase.web.CarController;

@SpringBootTest
class CarddatabaseApplicationTests {
	@Autowired
	private CarController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
