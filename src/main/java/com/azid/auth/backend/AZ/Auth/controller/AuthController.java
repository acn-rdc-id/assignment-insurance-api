package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.exceptions.BadRequestException;
import com.azid.auth.backend.AZ.Auth.exceptions.ForbiddenException;
import com.azid.auth.backend.AZ.Auth.model.AuthRequest;
import com.azid.auth.backend.AZ.Auth.model.User;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import com.azid.auth.backend.AZ.Auth.service.UserService;
import com.azid.auth.backend.AZ.Auth.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.registerUser(user);

        return ResponseEntity.ok(new ApiResponseDto<Map<String, Object>>("Success", HttpStatus.OK.value(), "User registered successfully!", Collections.emptyMap()));
    }

   /* @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ForbiddenException("User not found with email: " + authRequest.getEmail()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails);

        return ResponseEntity.ok(new ApiResponseDto<Map<String, Object>>("Success", HttpStatus.OK.value(), "User logged in Successfully!", Map.of("token", jwtToken, "email", user.getEmail(), "username", user.getUsername(), "userId", user.getUserId())));
    }*/
   @PostMapping("/login")
   public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
       // Validate incoming request
       if (authRequest.getEmail() == null || authRequest.getEmail().isBlank() || authRequest.getPassword() == null || authRequest.getPassword().isBlank()) {
           throw new BadRequestException("Email and password must not be empty.");
       }

       // Fetch user from the repository
       User user = userRepository.findByEmail(authRequest.getEmail())
               .orElseThrow(() -> new ForbiddenException("User not found with email: " + authRequest.getEmail()));

       // Authenticate user credentials
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

       // Get user details from authentication principal
       UserDetails userDetails = (UserDetails) authentication.getPrincipal();

       // Generate JWT token
       String jwtToken = jwtUtils.generateJwtToken(userDetails);

       // Ensure all necessary data is available before proceeding
       if (jwtToken == null || user.getEmail() == null || user.getUsername() == null || user.getUserId() == null) {
           throw new IllegalStateException("Unable to generate response due to missing user information.");
       }

       // Build response data
       Map<String, Object> responseData = Map.of(
               "token", jwtToken,
               "email", user.getEmail(),
               "username", user.getUsername(),
               "userId", user.getUserId()
       );

       // Return success response
       return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "User logged in successfully!", responseData));
   }

}
