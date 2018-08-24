package br.com.redis.sample.as.data.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration standaloneConfiguration = 
				new RedisStandaloneConfiguration("localhost", 6379);
		return new JedisConnectionFactory(standaloneConfiguration);
	}
	
}