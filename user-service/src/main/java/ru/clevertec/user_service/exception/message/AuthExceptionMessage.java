package ru.clevertec.user_service.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthExceptionMessage {

    public static final String USERNAME_ALREADY_EXIST = "This username already exists";

}
