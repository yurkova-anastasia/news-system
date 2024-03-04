package ru.clevertec.news_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.clevertec.news_service.controller.response.user.UserResponse;

/**
 * This interface defines the contract for communicating with the Users Service.
 *
 * @author Yurkova Anastasia
 */
@FeignClient(name = "users", url = "${client.users.url}")
public interface UserClient {

    /**
     * This method is used to retrieve the user details based on the provided authentication token.
     *
     * @param token the authentication token
     * @return the user details
     */
    @PostMapping("/users")
    UserResponse getUserDetails(@RequestHeader("Authorization") String token);

}
