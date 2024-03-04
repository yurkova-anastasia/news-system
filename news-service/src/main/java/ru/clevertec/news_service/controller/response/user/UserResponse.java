package ru.clevertec.news_service.controller.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Data class that represents a user's response.
 *
 */
@Data
@AllArgsConstructor
@Builder
public class UserResponse {

    @Schema(description = "Username of the user")
    private String username;

    @Schema(description = "Role of the user")
    private String role;

    @Schema(description = "List of authorities granted to the user")
    private List<String> authorities;

}

