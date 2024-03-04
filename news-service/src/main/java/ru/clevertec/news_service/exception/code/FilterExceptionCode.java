package ru.clevertec.news_service.exception.code;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterExceptionCode {

    public static final String INCORRECT_FILTER = "400001";
    public static final String INCORRECT_FILTER_TYPE = "400002";
    public static final String INCORRECT_FIELD = "400003";

}
