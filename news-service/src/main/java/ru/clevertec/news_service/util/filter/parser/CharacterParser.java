package ru.clevertec.news_service.util.filter.parser;

import org.springframework.stereotype.Component;

@Component
public class CharacterParser implements ValueParser {

    @Override
    public Object getReducedValue(String s) {
        return s.charAt(0);
    }

    @Override
    public Class<?> getValueClass() {
        return Character.class;
    }

}
