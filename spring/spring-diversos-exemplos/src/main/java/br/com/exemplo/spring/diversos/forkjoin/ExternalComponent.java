package br.com.exemplo.spring.diversos.forkjoin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalComponent {

	@Autowired
	private ExternalServiceFactory externalServiceFactory;
	
	public void callExternalServicesInParallel() {
		List<ExternalService> externalServices = this.externalServiceFactory.createExternalServices();
		ForkJoinPool forkJoinPool = new ForkJoinPool(externalServices.size());
		try {
			forkJoinPool.submit(() -> externalServices.parallelStream().forEach(externalService -> {
				externalService.call();
			})).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
