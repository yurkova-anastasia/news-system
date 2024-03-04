package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NotInPredicate<T> implements PredicateGenerator<T> {

    private final InPredicate<T> inPredicate;

    @Autowired
    public NotInPredicate(InPredicate<T> inPredicate) {
        this.inPredicate = inPredicate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        return builder.not(inPredicate.toPredicate(root, builder, criteria));
    }

    @Override
    public String getName() {
        return "NOT_IN";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "NOT " + inPredicate.toSql(values, key);
    }

}
