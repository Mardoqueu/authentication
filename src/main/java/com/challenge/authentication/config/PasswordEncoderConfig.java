package com.challenge.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up the password encoder.
 * This class is annotated with @Configuration, indicating that it is a
 * source of bean definitions for the application context.
 * It declares a single bean of type PasswordEncoder which applies
 * the BCrypt hashing algorithm for password encoding.
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
