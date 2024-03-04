package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MatchPredicate<T> implements PredicateGenerator<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        String comparisonString = criteria.getSearchUnitValues().get(0).toString();
        Path<Object> path = getPath(root, criteria);

        return builder.like(builder.lower(path.as(String.class)), comparisonString.toLowerCase());
    }

    @Override
    public String getName() {
        return "MATCH";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "LIKE " + "LOWER('" + values.get(0) + "')";
    }

}
