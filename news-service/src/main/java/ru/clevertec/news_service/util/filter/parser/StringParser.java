package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class StringParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return s;
    }

    @Override
    public Class<?> getValueClass() {
        return String.class;
    }

}
