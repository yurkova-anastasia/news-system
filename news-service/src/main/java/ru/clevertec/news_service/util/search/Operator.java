package ru.clevertec.news_service.util.search;

import lombok.Getter;


@Getter
public enum Operator {
    AND("&", " "),
    OR("|", ","),
    FOLLOW_BY("<->", " ");

    private final String delimiter;
    private final String splitter;

    Operator(String delimiter, String splitter) {
        this.delimiter = delimiter;
        this.splitter = splitter;
    }

}
