package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class DoubleParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return Double.parseDouble(s.trim());
    }

    @Override
    public Class<?> getValueClass() {
        return Double.class;
    }

}
