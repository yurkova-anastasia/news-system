package ru.clevertec.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.clevertec.user_service.controller.request.SignInRequest;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.controller.response.JwtAuthenticationResponse;
import ru.clevertec.user_service.entity.User;
import ru.clevertec.user_service.service.AuthenticationService;
import ru.clevertec.user_service.service.JwtService;
import ru.clevertec.user_service.service.UserService;

/**
 * The {@code AuthenticationServiceImpl} class implements the
 * {@link AuthenticationService} interface and provides the business logic for
 * user authentication and authorization.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Creates a new user account and generates a JSON Web Token (JWT) for the
     * user.
     *
     * @param signUpRequest the request body containing the user information
     * @return a JSON Web Token (JWT) containing the user's authentication
     * information
     */
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        User user = userService.create(signUpRequest);
        String token = jwtService.generateToken(user.getUsername());
        return new JwtAuthenticationResponse(token);
    }

    /**
     * Authenticates a user using their username and password and generates a
     * JSON Web Token (JWT) for the user.
     *
     * @param signInRequest the request body containing the username and password
     * @return a JSON Web Token (JWT) containing the user's authentication
     * information
     */
    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ));
        UserDetails user = userService.loadUserByUsername(signInRequest.getUsername());
        String token = jwtService.generateToken(user.getUsername());
        return new JwtAuthenticationResponse(token);
    }
}
