package com.kafka.sample;

import com.kafka.sample.baeldung.MessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KafkaSampleApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(KafkaSampleApplication.class, args);

		MessageListener listener = applicationContext.getBean(MessageListener.class);
		while (true) {

		}
	}

}
