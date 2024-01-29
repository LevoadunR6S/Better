package com.pet.Better;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterApplication.class, args);
	}

}
