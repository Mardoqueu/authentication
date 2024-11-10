package com.challenge.authentication.service;

import com.challenge.authentication.dto.UserDTO;
import com.challenge.authentication.entity.User;
import com.challenge.authentication.mapper.UserMapper;
import com.challenge.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserServiceTest is a test class for the UserService component.
 *
 * This test class uses Mockito to mock dependencies and test the UserService methods.
 *
 * It verifies the behavior of UserService methods, ensuring functionality such as
 * user registration and checking for existing usernames works as expected.
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the successful registration of a new user in the UserService.
     *
     * This method verifies that when a new user is being registered:
     * - The user does not exist in the repository.
     * - The password is encoded correctly.
     * - The user is saved successfully in the repository with the expected properties.
     * - The correct methods are invoked on the UserRepository and PasswordEncoder mocks.
     *
     * The method performs the following steps:
     * 1. Creates a new UserDTO object with a sample username and password.
     * 2. Mocks the UserRepository to return an empty Optional when checking for an existing username.
     * 3. Mocks the PasswordEncoder to return an encoded password for the given password.
     * 4. Mocks the UserRepository save method to assign an ID to the saved user and return it.
     * 5. Calls the UserService's saveUser method with the UserDTO.
     * 6. Asserts that the returned user is not null, has the expected username and encoded password,
     *    and that the save method of the UserRepository was called once.
     */
    @Test
    void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testUser");
        userDTO.setPassword("testPass");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPass")).thenReturn("encodedPassword");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });


        User registeredUser = userService.saveUser(userDTO);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("encodedPassword", registeredUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Test method for user registration when the username already exists.
     *
     * This test case:
     * - Sets up a UserDTO with a username and password.
     * - Mocks an existing user in the UserRepository with the same username.
     * - Asserts that a RuntimeException is thrown when attempting to save the new user.
     * - Verifies that the UserRepository save method is never called.
     */
    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testUser");
        userDTO.setPassword("testPass");

        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setPassword("existingPass");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(userDTO);
        });

        assertEquals("User already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
