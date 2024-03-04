package ru.clevertec.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.entity.User;
import ru.clevertec.user_service.exception.code.AuthExceptionCode;
import ru.clevertec.user_service.exception.message.AuthExceptionMessage;
import ru.clevertec.user_service.mapper.UserMapper;
import ru.clevertec.user_service.repository.UserRepository;
import ru.clevertec.user_service.service.UserService;

/**
 * Implementation of the UserService interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user in the system.
     *
     * @param signUpRequest the request containing the user's information
     * @return the newly created user
     */
    @Override
    public User create(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ApplicationException(AuthExceptionCode.USERNAME_ALREADY_EXIST,
                    AuthExceptionMessage.USERNAME_ALREADY_EXIST);
        }
        User user = userMapper.toEntity(signUpRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves a user from the system based on their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(username));
    }
}
