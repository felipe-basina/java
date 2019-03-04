package br.com.sample.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectConfig {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before(value = "execution(* br.com.sample.aop.service.*.*(..))")
	public void before(JoinPoint joinPoint) {
		// Advice
		logger.info("Check/Validating...");
		logger.info("Allowed execution for {}", joinPoint);
	}

	@After(value = "execution(* br.com.sample.aop.service.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("After execution of {}", joinPoint);
	}

	@AfterReturning(value = "execution(* br.com.sample.aop.service.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returned with value {}", joinPoint, result);
	}

	@Around(value = "@annotation(br.com.sample.aop.custom.annotations.TrackTime)")
	public String around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object value = joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time Taken by {} is {}. Returned value: {}", joinPoint, timeTaken, value);
		return String.valueOf(value);
	}

}
