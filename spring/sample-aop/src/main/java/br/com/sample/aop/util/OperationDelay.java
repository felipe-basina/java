package br.com.sample.aop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationDelay {

	private static final Logger logger = LoggerFactory.getLogger(OperationDelay.class);
	
	private static OperationDelay instance = new OperationDelay();
	
	public static void delayFor(final long timeInMilliseconds) {
		try {
			long positiveTimeInMilliseconds = instance.convertToPositiveNumber(timeInMilliseconds);
			Thread.sleep(positiveTimeInMilliseconds);
		} catch (Exception e) {
			logger.error("Error when delaying for {}(ms)", timeInMilliseconds, e);
		}
	}
	
	private boolean isNegativeNumber(final long timeInMilliseconds) {
		return timeInMilliseconds < 0 ? true : false;
	}
	
	private long numberModule(final long timeInMilliseconds) {
		return -1 * timeInMilliseconds;
	}
	
	private long convertToPositiveNumber(final long timeInMilliseconds) {
		if (this.isNegativeNumber(timeInMilliseconds)) {
			return this.numberModule(timeInMilliseconds);
		}
		return timeInMilliseconds;
	}
	
}
