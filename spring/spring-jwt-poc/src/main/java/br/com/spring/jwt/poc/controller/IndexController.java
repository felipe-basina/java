package br.com.spring.jwt.poc.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping(path = "/home")
	public String index() {
		return "Hello: ".concat(LocalDateTime.now().toString());
	}
	
}
