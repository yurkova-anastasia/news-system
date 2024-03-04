package ru.clevertec.news_service.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@ReadingConverter
public class BytesToDateMapper implements Converter<byte[], Timestamp> {

    @Override
    public Timestamp convert(final byte[] source) {
        String value = new String(source);
        return new Timestamp(Long.parseLong(value));
    }

}
