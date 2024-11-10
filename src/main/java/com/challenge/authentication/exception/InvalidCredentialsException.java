package com.challenge.authentication.exception;

/**
 * Exception thrown when an attempt to authenticate with invalid credentials occurs.
 *
 * This exception extends RuntimeException and is typically used in authentication
 * contexts to indicate that the provided credentials do not match any existing user records.
 *
 * @see RuntimeException
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}