package br.com.redis.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserRest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId) {
		logger.info("Getting user with ID {}.", userId);
		return userRepository.findById(Long.valueOf(userId)).orElse(null);
	}

}
