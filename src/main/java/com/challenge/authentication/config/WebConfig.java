package com.challenge.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class that implements WebMvcConfigurer to customize the
 * Spring Web MVC framework. This class sets up Cross-Origin Resource Sharing (CORS)
 * settings for the application.
 *
 * The addCorsMappings method configures the allowed origins, HTTP methods,
 * and headers for CORS requests. It applies the following configurations:
 *
 * - Allows requests from any origin ("*").
 * - Allows the following HTTP methods: GET, POST, PUT, DELETE, OPTIONS.
 * - Allows any headers ("*").
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

}
