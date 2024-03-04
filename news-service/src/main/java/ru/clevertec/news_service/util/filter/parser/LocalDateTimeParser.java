package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        LocalDateTime date;
        try {
            date = LocalDate.parse(s).atStartOfDay();
        } catch (Exception e) {
            date = LocalDateTime.parse(s);
        }
        return date;
    }

    @Override
    public Class<?> getValueClass() {
        return LocalDateTime.class;
    }

}
