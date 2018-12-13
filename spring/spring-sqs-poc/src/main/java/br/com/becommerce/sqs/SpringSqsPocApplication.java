package br.com.becommerce.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.becommerce.sqs.dto.MessageDTO;
import br.com.becommerce.sqs.producer.SQSMessageProducer;

@SpringBootApplication
public class SpringSqsPocApplication implements CommandLineRunner {

	@Autowired
	private SQSMessageProducer messageProducer;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSqsPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int index = 0; index <= 5; index++) {
			this.messageProducer.sendMessage("A message #" + index);
			this.messageProducer.sendMessage(new MessageDTO("A message #" + index));
		}
	}
	
}

