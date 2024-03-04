package ru.clevertec.user_service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.entity.Role;
import ru.clevertec.user_service.entity.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }


    @Test
    public void toEntity_shouldSetAllTheFieldsCorrectly() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("username")
                .email("email")
                .password("password")
                .firstname("firstname")
                .lastname("lastname")
                .role(Role.ADMIN)
                .build();

        User user = userMapper.toEntity(signUpRequest);

        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getFirstname()).isEqualTo("firstname");
        assertThat(user.getLastname()).isEqualTo("lastname");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

}