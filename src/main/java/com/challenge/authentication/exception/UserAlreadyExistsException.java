package com.challenge.authentication.exception;

/**
 * Exception thrown when an attempt is made to create a user that already exists.
 *
 * This exception extends RuntimeException and is typically used in user management
 * contexts to signal that a user with the specified username already exists in the system.
 *
 * @see RuntimeException
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}