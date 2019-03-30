package br.com.exemplo.spring.diversos.forkjoin;

import java.time.OffsetDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around(value = "@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		logger.info("{} execution at {}", joinPoint.getSignature(), OffsetDateTime.now());
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		logger.info("{} executed in {}ms", joinPoint.getSignature(), executionTime);
		return proceed;
	}

}
