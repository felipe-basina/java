package br.com.exemplo.spring.diversos.forkjoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "ServiceA")
public class ExternalServiceA implements ExternalService {

	@Autowired
	private HttpService httpService;
	
	@Override
	@LogExecutionTime
	public String call() {
		return this.httpService.get("/endpointA", "Avalue");
	}

}
