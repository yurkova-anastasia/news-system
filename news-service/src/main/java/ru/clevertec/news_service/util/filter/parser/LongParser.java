package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class LongParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return Long.parseLong(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return Long.class;
    }

}
