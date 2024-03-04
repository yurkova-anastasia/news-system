package ru.clevertec.news_service.util.filter.parser;

import java.io.Serializable;

public interface ValueParser extends Serializable {

    Object getReducedValue(String s);

    Class<?> getValueClass();

}
