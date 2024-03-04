package ru.clevertec.user_service.service;

import ru.clevertec.user_service.controller.request.SignInRequest;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.controller.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
