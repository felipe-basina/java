package br.com.sample.aop.dao;

import org.springframework.stereotype.Repository;

@Repository
public class DaoOne {

	public String getValueOne() {
		return "One";
	}
	
}
