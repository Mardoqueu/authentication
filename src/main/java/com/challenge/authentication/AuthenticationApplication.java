package com.challenge.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the AuthenticationApplication.
 *
 * This class serves as the starting point for the Spring Boot application.
 * It contains the main method which is the entry point of the application.
 * When the application is started, the main method runs and initializes the
 * Spring ApplicationContext.
 */
@SpringBootApplication
public class AuthenticationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
