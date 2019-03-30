package br.com.exemplo.spring.diversos.forkjoin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ExternalServiceFactory {

	@Autowired
	@Qualifier(value = "ServiceA")
	private ExternalService serviceA;

	@Autowired
	@Qualifier(value = "ServiceB")
	private ExternalService serviceB;
	
	@Autowired
	@Qualifier(value = "ServiceC")
	private ExternalService serviceC;
	
	public List<ExternalService> createExternalServices() {
		return Arrays.asList(this.serviceA, this.serviceB, this.serviceC);
	}
	
}
