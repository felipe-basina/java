package br.com.redis.sample.as.data.storage;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisComponent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RedisTemplate<String, String> redisTemplate;

	private JsonConvert jsonConvert;
	
	public RedisComponent(RedisTemplate<String, String> redisTemplate,
			JsonConvert jsonConvert) {
		super();
		this.redisTemplate = redisTemplate;
		this.jsonConvert = jsonConvert;
	}
	
	public void addToRedis(final String key, final Object value) {
		this.redisTemplate.opsForValue().set(key, 
				this.jsonConvert.convertFromObjectToString(value));
		this.redisTemplate.expire(key, 4, TimeUnit.MINUTES);
	}
	
	public String getFromRedis(final String key) {
		return this.redisTemplate.opsForValue().get(key);
	}
	
	public void getAllValuesFromRedis() {
		Set<String> redisKeys = this.redisTemplate.keys("*");
		Iterator<String> it = redisKeys.iterator();
		while (it.hasNext()) {
	       String data = it.next();
	       logger.info("key: {}, value: {}", data, 
	    		   this.redisTemplate.opsForValue().get(data));
		}
	}

}
