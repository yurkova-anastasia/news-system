package ru.clevertec.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.user_service.controller.request.SignInRequest;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.controller.response.JwtAuthenticationResponse;
import ru.clevertec.user_service.controller.response.UserResponse;
import ru.clevertec.user_service.entity.Role;
import ru.clevertec.user_service.service.AuthenticationService;

import java.util.List;

/**
 * The AuthController class is a Spring REST controller that provides endpoints for user authentication and registration.
 * It uses the AuthenticationService to perform authentication and authorization tasks.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * The signUp method registers a new user with the system. It takes a SignUpRequest object as input, which contains
     * the user's details (username, password, email, etc.) and the roles they should be assigned. The method returns a
     * JwtAuthenticationResponse object, which contains the user's authentication token and other information.
     *
     * @param request the SignUpRequest object containing the user's details and roles
     * @return a ResponseEntity containing a JwtAuthenticationResponse object with the user's authentication token
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.OK);
    }

    /**
     * The signIn method authenticates a user and returns a JwtAuthenticationResponse object containing the user's
     * authentication token. It takes a SignInRequest object as input, which contains the user's username and password.
     *
     * @param request the SignInRequest object containing the user's username and password
     * @return a ResponseEntity containing a JwtAuthenticationResponse object with the user's authentication token
     */
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
    }

}
