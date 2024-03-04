package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class IsNullPredicate<T> implements PredicateGenerator<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        Path<Object> path = getPath(root, criteria);

        return builder.isNull(path);
    }

    @Override
    public String getName() {
        return "IS_NULL";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "IS NULL";
    }

}
