package ru.clevertec.user_service.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtExceptionMessage {
    public static final String INVALID_JWT = "Token is not valid.";

}
