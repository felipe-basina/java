package br.com.exemplo.spring.diversos.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/endpointC")
class EndpointsC {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/{val}")
	public String endpointC(@PathVariable(name = "val") String value) {
		logger.info("Called {}", this.getClass().getName());
		ProcessDelay.doDelay(10l);
		return "from endpoint C: ".concat(value);
	}
	
}