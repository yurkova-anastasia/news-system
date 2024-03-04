package ru.clevertec.news_service.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceExceptionMessage {

    public static final String NEWS_NOT_FOUND = "News was not found.";
    public static final String COMMENT_NOT_FOUND = "Comment was not found.";
}
