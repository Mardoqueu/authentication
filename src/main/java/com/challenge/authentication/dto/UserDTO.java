package com.challenge.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    /**
     * The userName field represents the username of the user.
     * It must not be blank and should have a length between 3 and 30 characters.
     * Validations:
     * - @NotBlank: Username cannot be blank.
     * - @Size: Username must be between 3 and 30 characters.
     */
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String userName;

    /**
     * The password field represents the password of the user.
     * It must not be blank and should have a maximum length of 10 characters.
     * Validations:
     * - @NotBlank: Password cannot be blank.
     * - @Size: The password must be less than 10 characters.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 10, message = "The password must be less than 10 characters")
    private String password;

    /**
     * Default constructor for UserDTO.
     * Initializes an instance of UserDTO without setting any fields.
     */
    public UserDTO() {
    }

    /**
     * Constructs a new UserDTO object with the specified username and password.
     *
     * @param password the password of the user.
     * @param userName the username of the user.
     */
    public UserDTO(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username for the user.
     *
     * @param userName the user's new username, must be between 3 and 30 characters
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password the new password to be set for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
