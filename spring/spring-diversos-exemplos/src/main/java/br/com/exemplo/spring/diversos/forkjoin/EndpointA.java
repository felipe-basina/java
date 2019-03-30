package br.com.exemplo.spring.diversos.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/endpointA")
public class EndpointA {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/{val}")
	public String endpointA(@PathVariable(name = "val") String value) {
		logger.info("Called {}", this.getClass().getName());
		return "from endpoint A: ".concat(value);
	}
	
}
