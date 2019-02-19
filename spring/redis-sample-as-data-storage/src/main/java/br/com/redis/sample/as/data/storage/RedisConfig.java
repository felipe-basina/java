package br.com.redis.sample.as.data.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig {

	@Value(value = "${spring.redis.host}")
	private String server;
	
	@Value(value = "${spring.redis.port}")
	private int port;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration standaloneConfiguration = 
				new RedisStandaloneConfiguration(this.server, this.port);
		return new JedisConnectionFactory(standaloneConfiguration);
	}
	
}