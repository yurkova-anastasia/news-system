package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class IsNotNullPredicate<T> implements PredicateGenerator<T> {


    private final IsNullPredicate<T> isNullPredicate;

    @Autowired
    public IsNotNullPredicate(IsNullPredicate<T> isNullPredicate) {
        this.isNullPredicate = isNullPredicate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        return builder.not(isNullPredicate.toPredicate(root, builder, criteria));
    }

    @Override
    public String getName() {
        return "IS_NOT_NULL";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "IS NOT NULL";
    }

}
