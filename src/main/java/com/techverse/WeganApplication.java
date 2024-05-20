package com.techverse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
 

@SpringBootApplication
  
public class WeganApplication {

	private final static Logger logger = LoggerFactory.getLogger(WeganApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(WeganApplication.class, args);
	}
	
}
