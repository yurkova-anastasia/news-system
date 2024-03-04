package ru.clevertec.news_service.util.search;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component
public class FullTextSearchParser {

    public String parse(String search) {
        return parseOr(search);
    }

    private String parseOr(String search) {
        return parseByOperator(search, Operator.OR, this::parseAnd).concat(":*");
    }

    private String parseAnd(String str) {
        return parseByOperator(str, Operator.AND, this::parseFollowBy);
    }

    private String parseFollowBy(String str) {
        return str.replace(Operator.FOLLOW_BY.getSplitter(), Operator.FOLLOW_BY.getDelimiter())
                .replace("\"", "");
    }

    private String parseByOperator(String str, Operator operator, UnaryOperator<String> mapLexeme) {
        return Arrays.stream(str.split(operator.getSplitter() + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))
                .filter(s -> !s.isBlank())
                .map(String::strip)
                .map(mapLexeme)
                .collect(Collectors.joining(":*" + operator.getDelimiter()));
    }

}
