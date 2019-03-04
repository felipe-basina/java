package br.com.sample.aop.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sample.aop.custom.annotations.TrackTime;
import br.com.sample.aop.dao.DaoOne;
import br.com.sample.aop.util.OperationDelay;

@Service
public class ServiceOne {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaoOne daoOne;
	
	@TrackTime
	public String calculate() {
		final String value = this.daoOne.getValueOne();
		OperationDelay.delayFor(new Random().nextInt(10));
		logger.info("In business: {}", value);
		return value;
	}
	
}
