package com.hibernate.performance;

import com.hibernate.performance.component.PerformanceComponent;
import com.hibernate.performance.service.PerformanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class HibernatePerformanceApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(HibernatePerformanceApplication.class, args);

		log.warn("Process initialized");

		PerformanceComponent performanceComponent = app.getBean(PerformanceComponent.class);
		performanceComponent.deleteData();
		performanceComponent.createData(10, 10);

		PerformanceService performanceService = app.getBean(PerformanceService.class);
		performanceService.selectingAnEntity();

		log.warn("Process ended");
		System.exit(1);
	}

}
