package ru.clevertec.news_service.exception.code;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceExceptionCode {

    public static final String NEWS_NOT_FOUND = "404001";

    public static final String COMMENT_NOT_FOUND = "404002";
}
