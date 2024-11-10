package com.challenge.authentication.controller;

import com.challenge.authentication.dto.AuthRequestDTO;
import com.challenge.authentication.dto.AuthResponseDTO;
import com.challenge.authentication.dto.UserDTO;
import com.challenge.authentication.exception.InvalidCredentialsException;
import com.challenge.authentication.jwt.JwtUtil;
import com.challenge.authentication.mapper.UserMapper;
import com.challenge.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user in the system.
     *
     * @param userDTO A UserDTO object containing the username and password of the user to be registered.
     * @return A ResponseEntity containing a UserDTO object of the newly registered user.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.saveUser(userDTO)));
    }

    /**
     * Authenticates a user with the provided credentials and generates a JWT token and user ID.
     *
     * @param request An AuthRequestDTO object containing the username and password of the user attempting to log in.
     * @return A ResponseEntity containing an AuthResponseDTO with the JWT token and the user ID upon successful authentication.
     * @throws Exception if authentication fails due to invalid credentials.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequestDTO request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        final UserDetails userDetails = userService.loadUserByUsername(request.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        final Long userId = userService.findUserIdByUsername(request.getUserName());

        return ResponseEntity.ok(new AuthResponseDTO(jwt, userId));
    }
}

