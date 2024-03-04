package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class EqualPredicate<T> implements PredicateGenerator<T> {

    @Override
    @SuppressWarnings("unchecked")
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        Object value = criteria.getSearchUnitValues().get(0);
        Path<?> path = getPath(root, criteria);

        if (criteria.getJavaType() == LocalDateTime.class) {
            return getLocalDateTimeBetween(builder, (Path<LocalDateTime>) path, value, value);
        }

        return builder.equal(path, value);
    }

    @Override
    public String getName() {
        return "EQUAL";
    }

    public String toSql(List<Object> values, String key) {
        LocalDate date;
        try {
            date = LocalDate.parse((String) values.get(0));
        } catch (Exception e) {
            return "= " + values.get(0);
        }
        return "= '" + date + "'";
    }

}
