package ru.clevertec.news_service.util.filter.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SearchCriteriaExpanded {

    private List<String> keyFields;
    private String operation;
    private List<Object> searchUnitValues;
    private Class<?> javaType;

    public SearchCriteriaExpanded(FieldCriteriaDto field) {
        this.keyFields = Arrays.asList(field.getField().split("[.]"));
        this.operation = field.getOperator();
        this.searchUnitValues = field.getValues();
    }

}
