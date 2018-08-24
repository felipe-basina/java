package br.com.redis.sample.as.data.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final RedisComponent redisComponent;

	@Autowired
	public UserRest(RedisComponent redisComponent) {
		this.redisComponent = redisComponent;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String getUser(@PathVariable String userId) {
		logger.info("Getting user with ID {}.", userId);
		return this.redisComponent.getFromRedis(userId);
	}

}
