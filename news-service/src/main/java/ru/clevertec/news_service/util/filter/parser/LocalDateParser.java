package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return LocalDate.parse(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return LocalDate.class;
    }

}
