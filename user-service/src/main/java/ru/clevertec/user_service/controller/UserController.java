package ru.clevertec.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.user_service.controller.response.UserResponse;
import ru.clevertec.user_service.entity.User;

import java.util.List;

/**
 * A controller for managing user accounts.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    /**
     * Returns the currently authenticated user.
     *
     * @param userDetails the currently authenticated user
     * @return a response containing the user's information
     */
    @PostMapping
    @SuppressWarnings("unchecked")
    public ResponseEntity<UserResponse> findUserByToken(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        String username = user.getUsername();
        String role = user.getRole();
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserResponse userResponse = new UserResponse(username, role, authorities);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
