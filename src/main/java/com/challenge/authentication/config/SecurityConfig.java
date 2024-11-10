package com.challenge.authentication.config;

import com.challenge.authentication.jwt.JwtRequestFilter;
import com.challenge.authentication.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Security configuration class that enables web security and configures various security settings
 * such as authentication manager and security filter chain.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates and configures an {@link AuthenticationManager} with a custom {@link UserService} and {@link PasswordEncoder}.
     * This allows the application to manage user authentication.
     *
     * @param http an {@link HttpSecurity} object used for configuring web-based security for specific HTTP requests.
     * @return an {@link AuthenticationManager} object used for user authentication within the application.
     * @throws Exception if an error occurs while configuring the {@link AuthenticationManagerBuilder}.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    /**
     * Configures the SecurityFilterChain with custom security settings. This includes configuring CORS, disabling CSRF,
     * setting up authorization rules, and adding JWT request filters.
     *
     * @param http an HttpSecurity object used to configure web-based security for specific HTTP requests
     * @return a SecurityFilterChain object representing the filter chain used by Spring Security
     * @throws Exception if an error occurs while configuring the HttpSecurity object
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of(
                            "https://calc-front-eta.vercel.app/",
                            "https://calculator-chanllenge.vercel.app/"
                    ));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    return corsConfig;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
