package ru.clevertec.news_service.util.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.clevertec.news_service.exception.code.FilterExceptionCode;
import ru.clevertec.news_service.exception.message.FilterExceptionMessage;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.news_service.util.filter.dto.FieldCriteriaDto;
import ru.clevertec.news_service.util.filter.dto.QueryDto;
import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import ru.clevertec.news_service.util.filter.predicates.PredicateGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class FilterSpecification<T> implements Specification<T> {

    private FilterParser filterParser;
    private Map<String, PredicateGenerator<T>> operationGeneratorMap = new HashMap<>();
    private QueryDto queryDto;

    public static final String OR = "OR";

    @Autowired
    public FilterSpecification(FilterParser filterParser) {
        this.filterParser = filterParser;
    }

    public void register(String typeOperation, PredicateGenerator<T> predicateGenerator) {
        operationGeneratorMap.put(typeOperation, predicateGenerator);
    }

    public Specification<T> getSpecification(String filter) {
        if (filter == null) {
            return Specification.where(null);
        }
        this.queryDto = filterParser.parseQuery(filter);
        return this;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<T> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder builder
    ) {
        Predicate predicate = buildPredicate(root, builder, queryDto);

        if (predicate == null) {
            return builder.conjunction();
        }
        Predicate[] predicates = Stream.of(predicate).toArray(Predicate[]::new);

        if (OR.equals(queryDto.getConcatType())) {
            return builder.or(predicates);
        }

        return builder.and(predicates);
    }

    private Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, QueryDto queryDto) {
        if (queryDto.getSubQueries() != null) {
            return getSubQueryPredicates(root, builder, queryDto);
        }

        if (queryDto.getFields() == null) {
            return builder.conjunction();
        }

        return getFieldPredicates(root, builder, queryDto);
    }

    private Predicate getSubQueryPredicates(Root<T> root, CriteriaBuilder builder, QueryDto queryDto) {
        Predicate[] subQueryPredicates = queryDto.getSubQueries().stream()
                .map(subQuery -> OR.equals(subQuery.getConcatType())
                        ? builder.or(buildPredicate(root, builder, subQuery))
                        : builder.and(buildPredicate(root, builder, subQuery)))
                .toArray(Predicate[]::new);

        return builder.and(subQueryPredicates);
    }

    private Predicate getFieldPredicates(Root<T> root, CriteriaBuilder builder, QueryDto queryDto) {
        Predicate[] fieldPredicates = queryDto.getFields().stream()
                .map(field -> operationGeneratorMap.get(field.getOperator())
                        .toPredicate(root, builder, toSearchCriteria(root, field)))
                .toArray(Predicate[]::new);

        return OR.equals(queryDto.getConcatType())
                ? builder.or(fieldPredicates)
                : builder.and(fieldPredicates);
    }

    private SearchCriteriaExpanded toSearchCriteria(Root<T> root, FieldCriteriaDto fieldCriteriaDto) {
        SearchCriteriaExpanded criteriaExpanded = new SearchCriteriaExpanded(fieldCriteriaDto);
        Class<?> javaType = getJavaType(criteriaExpanded, root);
        criteriaExpanded.setJavaType(javaType);
        List<Object> searchUnits = criteriaExpanded.getSearchUnitValues().stream()
                .map(value -> filterParser.convertValue(value.toString(), javaType))
                .toList();
        criteriaExpanded.setSearchUnitValues(searchUnits);

        return criteriaExpanded;
    }

    private Class<?> getJavaType(SearchCriteriaExpanded criteriaExpanded, Root<T> root) {
        String key = criteriaExpanded.getKeyFields().get(0);
        try {
            Path<?> path = root.get(key);
            int fieldSize = criteriaExpanded.getKeyFields().size();

            for (int i = 1; i <= fieldSize - 1; i++) {
                String key2 = criteriaExpanded.getKeyFields().get(i);
                path = path.get(key2);
            }

            return path.getJavaType();
        } catch (PathElementException ex) {
            throw new ApplicationException(FilterExceptionCode.INCORRECT_FIELD,
                    FilterExceptionMessage.INCORRECT_FIELD + key);
        }
    }

}
