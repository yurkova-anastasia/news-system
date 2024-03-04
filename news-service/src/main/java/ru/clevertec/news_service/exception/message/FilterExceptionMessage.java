package ru.clevertec.news_service.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterExceptionMessage {
    public static final String INCORRECT_FILTER = "Incorrect filter.";
    public static final String INCORRECT_FILTER_TYPE = "Incorrect filter type.";
    public static final String INCORRECT_FIELD = "Incorrect search field: ";

}
