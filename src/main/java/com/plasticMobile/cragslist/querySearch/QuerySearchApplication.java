package com.plasticMobile.cragslist.querySearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.plasticMobile.cragslist.services"})
public class QuerySearchApplication {

	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(QuerySearchApplication.class, args);
	
	}
	

}
