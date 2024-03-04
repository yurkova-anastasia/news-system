package ru.clevertec.news_service.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizedExceptionMessage {

    public static final String ACCESS_DENIED = "The user can only edit his own news/comments";

}
