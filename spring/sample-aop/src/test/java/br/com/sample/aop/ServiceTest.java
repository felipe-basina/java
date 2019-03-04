package br.com.sample.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sample.aop.service.ServiceOne;
import br.com.sample.aop.service.ServiceTwo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ServiceOne serviceOne;
	
	@Autowired
	private ServiceTwo serviceTwo;
	
	@Test
	public void testServiceOne() {
		logger.info(this.serviceOne.calculate());
	}
	
	@Test
	public void testServiceTwo() {
		logger.info(this.serviceTwo.calculate());
	}

}
