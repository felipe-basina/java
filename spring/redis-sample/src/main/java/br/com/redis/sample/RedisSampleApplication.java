package br.com.redis.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisSampleApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final UserRepository userRepository;
	
	@Autowired
	public RedisSampleApplication(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    logger.info("Persistindo registros. Total de registros atual: {}.", userRepository.count());
	    userRepository.save(new User("Usuario 01", 2000));
	    userRepository.save(new User("Usuario 02", 29000));
	    userRepository.save(new User("Usuario 03", 550));
	    logger.info("Registros persistidos. Registros: {}.", userRepository.findAll());
	}
}
