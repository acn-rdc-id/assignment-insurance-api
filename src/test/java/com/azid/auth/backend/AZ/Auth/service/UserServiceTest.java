package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.model.User;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void registerUser_validResponse() {
        User user = mockUser();

        doReturn(Optional.empty())
                .doReturn(Optional.of(user))
                .when(userRepository).findByEmail(any());

        userService.registerUser(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerUser_duplicateEmail() {
        User user = mockUser();

        doReturn(Optional.of(user))
                .when(userRepository).findByEmail(any());

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void registerUser_userNotSaved() {
        User user = mockUser();

        doReturn(Optional.empty())
                .doReturn(Optional.empty())
                .when(userRepository).findByEmail(any());

        assertThrows(IllegalStateException.class, () -> userService.registerUser(user));
    }

    @Test
    void authenticateUser_validResponse() {
        User user = mockUser();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(Boolean.TRUE);

        User result = userService.authenticateUser("email", "password");
        assertNotNull(result);
    }

    @Test
    void getUserByUserId_validResponse() {
        User user = mockUser();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));

        User result = userService.getUserByUserId("userId");
        assertNotNull(result);
    }

    private User mockUser() {
        User user = new User();
        user.setUsername("userName");
        user.setEmail("email");
        user.setPassword("password");

        return user;
    }
}
