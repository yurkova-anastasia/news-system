package ru.clevertec.user_service.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.user_service.entity.Role;

@Data
@Schema(description = "Registration Request")
@Builder
public class SignUpRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 5, max = 100, message = "Username must contain from 5 to 100 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Email address", example = "jondoe@gmail.com ")
    @Size(min = 5, max = 100, message = "The email address must contain from 5 to 100 characters")
    @NotBlank(message = "The email address cannot be empty")
    @Email(message = "The email address must be in the format user@example.com ")
    private String email;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length should be no more than 255 characters")
    private String password;

    private String firstname;

    private String lastname;

    @Schema(description = "User Role", example = "ADMIN")
    private Role role;

}