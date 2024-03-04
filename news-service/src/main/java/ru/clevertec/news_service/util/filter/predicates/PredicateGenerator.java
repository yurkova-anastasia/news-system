package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.FilterSpecification;
import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface PredicateGenerator<T> extends Serializable {

    Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria);

    String getName();

    String toSql(List<Object> values, String key);

    @Autowired
    default void registerMyself(FilterSpecification<T> filterSpecification) {
        filterSpecification.register(getName(), this);
    }

    default Predicate getLocalDateTimeBetween(CriteriaBuilder builder, Path<LocalDateTime> path, Object value1,
                                              Object value2) {
        LocalDateTime date1 = (LocalDateTime) value1;
        LocalDateTime date2 = (LocalDateTime) value2;
        return builder.between(path, LocalDateTime.of(date1.toLocalDate(), LocalTime.MIN),
                               LocalDateTime.of(date2.toLocalDate(), LocalTime.MAX));
    }

    default Path<Object> getPath(Root<T> root, SearchCriteriaExpanded criteria) {
        String key = criteria.getKeyFields().get(0);
        Path<Object> path = root.get(key);
        int fieldSize = criteria.getKeyFields().size();

        for (int i = 1; i <= fieldSize - 1; i++) {
            String key2 = criteria.getKeyFields().get(i);
            path = path.get(key2);
        }

        return path;
    }

}
