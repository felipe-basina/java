package br.com.sample.aop.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sample.aop.custom.annotations.TrackTime;
import br.com.sample.aop.dao.DaoTwo;
import br.com.sample.aop.util.OperationDelay;

@Service
public class ServiceTwo {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaoTwo daoTwo;
	
	@TrackTime
	public String calculate() {
		final String value = this.daoTwo.getValueTwo();
		OperationDelay.delayFor(new Random(10).nextInt(10));
		logger.info("In business: {}", value);
		return value;
	}
	
}
