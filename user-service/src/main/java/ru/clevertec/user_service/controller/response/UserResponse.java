package ru.clevertec.user_service.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String role;
    private List<String> authorities;

}
