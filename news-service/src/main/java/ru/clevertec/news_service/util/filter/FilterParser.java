package ru.clevertec.news_service.util.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.news_service.exception.code.FilterExceptionCode;
import ru.clevertec.news_service.exception.message.FilterExceptionMessage;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.news_service.util.filter.dto.QueryDto;
import ru.clevertec.news_service.util.filter.parser.ValueParser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilterParser {

    private final ObjectMapper objectMapper;
    private final List<ValueParser> valueParsers;

    public QueryDto parseQuery(String query) {
        try {
            return objectMapper.readValue(query, new TypeReference<>() {
            });
        } catch (JsonProcessingException jsonProcessingException) {
            throw new ApplicationException(FilterExceptionCode.INCORRECT_FILTER,
                    FilterExceptionMessage.INCORRECT_FILTER);
        }
    }

    public Object convertValue(String value, Class<?> javaType) {
        if (value.charAt(0) == '0'
                || !value.replaceAll("[^%]", "").equalsIgnoreCase("")
                || value.equals("null")) {
            return value;
        }
        try {
            boolean hasJavaType = valueParsers.stream()
                    .anyMatch(parser -> parser.getValueClass().equals(javaType));

            return valueParsers.stream()
                    .collect(Collectors.toMap(ValueParser::getValueClass, Function.identity()))
                    .get(hasJavaType ? javaType : Object.class)
                    .getReducedValue(value);

        } catch (NumberFormatException e) {
            throw new ApplicationException(FilterExceptionCode.INCORRECT_FILTER_TYPE,
                    FilterExceptionMessage.INCORRECT_FILTER_TYPE
                            + getIncorrectTypeExceptionMessage(value, javaType));
        }
    }

    private String getIncorrectTypeExceptionMessage(String receivedValue, Class<?> modelAttribute) {
        return "Для параметра " + modelAttribute.getName() + " ожидаемый тип: " + modelAttribute
                .getSimpleName() + ", но фактический: " + receivedValue
                .getClass()
                .getSimpleName();
    }

}
