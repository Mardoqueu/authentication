package com.challenge.authentication.dto;

/**
 * Data Transfer Object representing the authentication response.
 *
 * This class encapsulates the JWT token and the associated user ID
 * that are returned upon successful authentication.
 */
public class AuthResponseDTO {
    private String token;
    private Long userId;

    public AuthResponseDTO(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

