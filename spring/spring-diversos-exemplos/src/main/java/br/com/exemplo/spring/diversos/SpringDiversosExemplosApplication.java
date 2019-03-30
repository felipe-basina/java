package br.com.exemplo.spring.diversos;

import java.time.OffsetDateTime;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import br.com.exemplo.spring.diversos.bulk.BulkService;
import br.com.exemplo.spring.diversos.forkjoin.ExternalComponent;

@SpringBootApplication
public class SpringDiversosExemplosApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringDiversosExemplosApplication.class);
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringDiversosExemplosApplication.class, args);
		callExternalComponent(applicationContext);
	}
	
	public static void callBulkService(ApplicationContext applicationContext) {
		BulkService bulkService = applicationContext.getBean(BulkService.class);
		bulkService.bulkInsert();
		bulkService.regularInsert();		
	}
	
	public static void callExternalComponent(ApplicationContext applicationContext) {
		ExternalComponent externalComponent = applicationContext.getBean(ExternalComponent.class);
		IntStream.range(0, 5)
			.forEach(idx -> {
				logger.info("Running idx {}", idx);
				externalComponent.callExternalServicesInParallel();
			});
		logger.info("Ended process at {}", OffsetDateTime.now());
	}

}
