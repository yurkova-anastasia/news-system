package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NotBetweenPredicate<T> implements PredicateGenerator<T> {

    private final BetweenPredicate<T> betweenPredicate;

    @Autowired
    public NotBetweenPredicate(BetweenPredicate<T> betweenPredicate) {
        this.betweenPredicate = betweenPredicate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        return builder.not(betweenPredicate.toPredicate(root, builder, criteria));
    }


    @Override
    public String getName() {
        return "NOT_BETWEEN";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "NOT " + betweenPredicate.toSql(values, key);
    }


}
