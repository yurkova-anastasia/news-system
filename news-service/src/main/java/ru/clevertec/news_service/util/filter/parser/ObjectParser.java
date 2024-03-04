package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class ObjectParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return s;
    }

    @Override
    public Class<?> getValueClass() {
        return Object.class;
    }

}
