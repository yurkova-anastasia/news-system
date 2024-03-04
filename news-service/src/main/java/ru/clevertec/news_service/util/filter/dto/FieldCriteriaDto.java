package ru.clevertec.news_service.util.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FieldCriteriaDto {

    private String field;
    private String operator;
    private List<Object> values;

}
