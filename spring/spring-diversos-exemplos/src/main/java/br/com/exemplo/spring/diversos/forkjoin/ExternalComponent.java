package br.com.exemplo.spring.diversos.forkjoin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalComponent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private List<ExternalService> externalServices;
	
	public void callExternalServicesInParallel() {
		ForkJoinPool forkJoinPool = new ForkJoinPool(externalServices.size());
		try {
			forkJoinPool.submit(() -> this.externalServices.parallelStream().forEach(externalService -> {
				logger.info("##### Instance of {} #####", externalService.getClass().getSimpleName());
				externalService.call();
			})).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
