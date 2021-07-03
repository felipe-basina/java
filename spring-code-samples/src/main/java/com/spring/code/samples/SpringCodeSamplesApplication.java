package com.spring.code.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringCodeSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCodeSamplesApplication.class, args);
	}

}
