package com.email.emailGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class EmailGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailGeneratorApplication.class, args);
	}
  @Bean
	public WebClient WC( ){
		return WebClient.builder().build();
  }
}
