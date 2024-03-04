package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class BooleanWrapperParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return Boolean.parseBoolean(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return Boolean.class;
    }

}
