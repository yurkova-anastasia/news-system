package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NotEqualPredicate<T> implements PredicateGenerator<T> {

    private final EqualPredicate<T> equalPredicate;

    @Autowired
    public NotEqualPredicate(EqualPredicate<T> equalPredicate) {
        this.equalPredicate = equalPredicate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        return builder.not(equalPredicate.toPredicate(root, builder, criteria));
    }

    @Override
    public String getName() {
        return "NOT_EQUAL";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "!" + equalPredicate.toSql(values, key);
    }

}
