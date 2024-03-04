package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class GreaterThanEqualPredicate<T> implements PredicateGenerator<T> {

    private final GreaterThanPredicate<T> greaterThanPredicateHolder;
    private final EqualPredicate<T> equalPredicateHolder;

    @Autowired
    public GreaterThanEqualPredicate(GreaterThanPredicate<T> greaterThanPredicateHolder,
                                     EqualPredicate<T> equalPredicateHolder) {
        this.greaterThanPredicateHolder = greaterThanPredicateHolder;
        this.equalPredicateHolder = equalPredicateHolder;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        Predicate greaterThanPredicate = greaterThanPredicateHolder.toPredicate(root, builder, criteria);
        Predicate equalPredicate = equalPredicateHolder.toPredicate(root, builder, criteria);
        return builder.or(greaterThanPredicate, equalPredicate);
    }


    @Override
    public String getName() {
        return "GREATER_THAN_EQUAL";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        LocalDate date;
        try {
            date = LocalDate.parse((String) values.get(0));
        } catch (Exception e) {
            return ">= " + values.get(0);
        }
        return ">= '" + date + "'";
    }

}
