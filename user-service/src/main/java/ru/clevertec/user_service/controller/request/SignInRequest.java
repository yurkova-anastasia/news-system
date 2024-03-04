package ru.clevertec.user_service.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
@Builder
public class SignInRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 5, max = 100, message = "Username must be at least 5 characters long")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Password length must be from 8 to 255 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}