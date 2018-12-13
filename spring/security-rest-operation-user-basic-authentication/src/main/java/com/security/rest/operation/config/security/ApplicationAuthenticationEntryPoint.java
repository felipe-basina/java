package com.security.rest.operation.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class ApplicationAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	public static final String APPLICATION_REALM = "APP_REALM";
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + this.getRealmName() + "");
        PrintWriter writer = response.getWriter();
        writer.println(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), 
        		authException.getMessage()));
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.setRealmName(APPLICATION_REALM);
		super.afterPropertiesSet();
	}
	
}

class ErrorMessage {
	
	private int httpStatus;
	
	private String message;
	
	private String timestamp = LocalDateTime.now().toString();

	public ErrorMessage(int httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorMessage [httpStatus=").append(httpStatus).append(", message=").append(message)
				.append(", timestamp=").append(timestamp).append("]");
		return builder.toString();
	}
	
}