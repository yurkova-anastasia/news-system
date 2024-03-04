package ru.clevertec.user_service.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import ru.clevertec.user_service.controller.request.SignInRequest;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.controller.response.JwtAuthenticationResponse;
import ru.clevertec.user_service.entity.Role;
import ru.clevertec.user_service.entity.User;
import ru.clevertec.user_service.service.JwtService;
import ru.clevertec.user_service.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void signUp_shouldCreateUserAndReturnToken() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("username")
                .email("jondoe@gmail.com")
                .password("password")
                .firstname("name")
                .lastname("surname")
                .role(Role.ADMIN)
                .build();
        User user = User.builder()
                .username("username")
                .email("jondoe@gmail.com")
                .password("password")
                .firstname("name")
                .lastname("surname")
                .role(Role.ADMIN)
                .build();
        String expected = "token";
        when(userService.create(signUpRequest)).thenReturn(user);
        when(jwtService.generateToken(user.getUsername())).thenReturn(expected);

        JwtAuthenticationResponse actual = authenticationService.signUp(signUpRequest);

        assertEquals(expected, actual.getToken());
        verify(userService).create(signUpRequest);
        verify(jwtService).generateToken(anyString());
    }

    @Test
    void signIn_shouldReturnToken() {
        SignInRequest signInRequest = SignInRequest.builder()
                .username("username")
                .password("password")
                .build();
        UserDetails userDetails = mock(UserDetails.class);
        when(userService.loadUserByUsername(signInRequest.getUsername())).thenReturn(userDetails);
        String token = "token";
        when(jwtService.generateToken(userDetails.getUsername())).thenReturn(token);

        JwtAuthenticationResponse actual = authenticationService.signIn(signInRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails.getUsername());
        assertEquals(token, actual.getToken());
    }

}