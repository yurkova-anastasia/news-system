package ru.clevertec.user_service.exception.code;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtExceptionCode {

    public static final String INVALID_JWT = "400001";

}
