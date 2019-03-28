package br.com.exemplo.spring.diversos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import br.com.exemplo.spring.diversos.bulk.BulkService;

@SpringBootApplication
public class SpringDiversosExemplosApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringDiversosExemplosApplication.class, args);
		BulkService bulkService = applicationContext.getBean(BulkService.class);
		bulkService.bulkInsert();
		//bulkService.regularInsert();
	}

}
