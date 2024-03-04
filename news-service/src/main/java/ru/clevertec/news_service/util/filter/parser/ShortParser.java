package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class ShortParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return Short.parseShort(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return Short.class;
    }

}
