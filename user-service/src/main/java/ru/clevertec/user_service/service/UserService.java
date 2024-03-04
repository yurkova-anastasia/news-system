package ru.clevertec.user_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.entity.User;

public interface UserService extends UserDetailsService {

    User create(SignUpRequest signUpRequest);

}
