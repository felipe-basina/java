package br.com.spring.jwt.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringJwtPocApplication implements CommandLineRunner {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment env;
	
	@Value(value = "${aws.access.key}")
	private String accessKey;
	
	@Value(value = "${aws.secret.key}")
	private String secretKey;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJwtPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Environment value: {}, {}, {}, {}", 
				this.env.getProperty("aws.secret.key"),
				this.env.getProperty("aws.access.key"),
				this.secretKey, this.accessKey);
	}
	
}
