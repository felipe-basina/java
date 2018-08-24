package br.com.redis.sample.as.data.storage;

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
	
	private final RedisComponent redisComponent;
	
	@Autowired
	public RedisSampleApplication(RedisComponent redisComponent) {
		super();
		this.redisComponent = redisComponent;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.redisComponent.addToRedis("1", new User("Shubham", 2000));
		this.redisComponent.addToRedis("2", new User("Pankaj", 29000));
		this.redisComponent.addToRedis("3", new User("Lewis", 550));
	    logger.info("Done saving users");
	}
}
