package com.challenge.authentication.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Token) operations such as generating tokens,
 * extracting information from tokens, and validating tokens.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Generates a JWT token for the specified username.
     *
     * @param username the username for which to generate the JWT token
     * @return a JWT token as a String
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // Token valid for 2 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the JWT token
     */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates a JWT token against user details.
     *
     * @param token the JWT token to be validated
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid and belongs to the user, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param token the JWT token to be checked
     * @return true if the token has expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}

