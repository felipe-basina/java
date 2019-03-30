package br.com.exemplo.spring.diversos.forkjoin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

	private RestTemplate restTemplate = new RestTemplate();
	
	private final static String BASE_URL = "http://localhost:8080";
	
	public String get(final String uri, final String pathParam) {
		return this.restTemplate.exchange(BASE_URL.concat(uri).concat("/".concat(pathParam)), HttpMethod.GET,
				this.createHttpEntity(), String.class).getBody();
	}
	
	private HttpEntity<?> createHttpEntity() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(httpHeaders);
	}

}
