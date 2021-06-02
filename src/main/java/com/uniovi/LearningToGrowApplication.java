package com.uniovi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningToGrowApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningToGrowApplication.class, args);
	}

}
