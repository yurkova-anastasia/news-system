package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NotMatchPredicate<T> implements PredicateGenerator<T> {

    private final MatchPredicate<T> matchPredicate;

    @Autowired
    public NotMatchPredicate(MatchPredicate<T> matchPredicate) {
        this.matchPredicate = matchPredicate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        return builder.not(matchPredicate.toPredicate(root, builder, criteria));
    }

    @Override
    public String getName() {
        return "NOT_MATCH";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "NOT " + matchPredicate.toSql(values, key);
    }

}
