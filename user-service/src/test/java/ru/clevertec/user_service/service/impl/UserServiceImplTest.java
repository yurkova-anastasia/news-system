package ru.clevertec.user_service.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.entity.Role;
import ru.clevertec.user_service.entity.User;
import ru.clevertec.user_service.mapper.UserMapper;
import ru.clevertec.user_service.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void create() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("username")
                .email("jondoe@gmail.com")
                .password("password")
                .firstname("name")
                .lastname("surname")
                .role(Role.ADMIN)
                .build();
        User expected = User.builder()
                .username("testUsername")
                .password("password")
                .build();
        when(userMapper.toEntity(signUpRequest)).thenReturn(expected);
        when(passwordEncoder.encode(expected.getPassword())).thenReturn("password");
        when(userRepository.save(expected)).thenReturn(expected);

        User actual = userService.create(signUpRequest);

        verify(userRepository).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void loadUserByUsername() {
        String username = "testUsername";
        User expected = User.builder()
                .username(username)
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expected));

        User actual = userService.loadUserByUsername(username);

        assertEquals(expected, actual);
        verify(userRepository).findByUsername(username);
    }

}