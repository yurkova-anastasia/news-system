package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class IntegerParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return Integer.parseInt(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return Integer.class;
    }

}
