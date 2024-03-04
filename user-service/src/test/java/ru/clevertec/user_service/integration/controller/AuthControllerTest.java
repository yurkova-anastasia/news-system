package ru.clevertec.user_service.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.user_service.controller.request.SignInRequest;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.controller.response.JwtAuthenticationResponse;
import ru.clevertec.user_service.entity.Role;
import ru.clevertec.user_service.integration.TestContainer;
import ru.clevertec.user_service.service.AuthenticationService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest extends TestContainer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    private AuthenticationService authenticationService;

    private final String path = "/api/v1/auth";

    @Test
    @WithAnonymousUser
    void signUpTest_shouldReturnToken() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("username")
                .email("jondoe@gmail.com")
                .password("password")
                .firstname("name")
                .lastname("surname")
                .role(Role.ADMIN)
                .build();
        JwtAuthenticationResponse expected = JwtAuthenticationResponse.builder()
                .token("token")
                .build();
        doReturn(expected)
                .when(authenticationService).signUp(signUpRequest);

        mockMvc.perform(post(path + "/sign-up")
                        .content(mapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected)));
        verify(authenticationService).signUp(signUpRequest);
    }

    @Test
    @WithAnonymousUser
    void signInTest_shouldReturnToken() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .username("username")
                .password("password")
                .build();
        JwtAuthenticationResponse expected = JwtAuthenticationResponse.builder()
                .token("token")
                .build();
        doReturn(expected)
                .when(authenticationService).signIn(signInRequest);

        mockMvc.perform(post(path + "/sign-in")
                        .content(mapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected)));
        verify(authenticationService).signIn(signInRequest);
    }

}