package com.kafka.sample;

import com.kafka.sample.baeldung.MessageListener;
import com.kafka.sample.baeldung.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootApplication
public class KafkaSampleApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(KafkaSampleApplication.class, args);
		applicationContext.getBean(MessageListener.class);

		final MessageProducer messageProducer = applicationContext.getBean(MessageProducer.class);
		final String exampleTopicName = "example-topic";

		int index = 0;
		while (true) {
			messageProducer.sendMessage(String.format("A message %d at %s", ++index, LocalDateTime.now()),
					Optional.of(exampleTopicName));
			delay();
		}
	}

	private static void delay() {
		try {
			Thread.sleep(10 * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
