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
public class BetweenPredicate<T> implements PredicateGenerator<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        Class<?> javaType = criteria.getJavaType();
        Object value1 = criteria.getSearchUnitValues().get(0);
        Object value2 = criteria.getSearchUnitValues().get(1);
        Path<Object> path = getPath(root, criteria);
        return getBetweenPredicate(builder, javaType, path, value1, value2);
    }

    @SuppressWarnings("unchecked")
    private Predicate getBetweenPredicate(CriteriaBuilder builder, Class<?> javaType, Path<?> path,
                                          Object value1, Object value2) {
        if (javaType == Integer.class) {
            return builder.between((Path<Integer>) path, (Integer) value1, (Integer) value2);
        }

        if (javaType == Short.class) {
            return builder.between((Path<Short>) path, (Short) value1, (Short) value2);
        }

        if (javaType == Long.class) {
            return builder.between((Path<Long>) path, (Long) value1, (Long) value2);
        }

        if (javaType == LocalDate.class) {
            return builder.between((Path<LocalDate>) path, (LocalDate) value1, (LocalDate) value2);
        }

        if (javaType == LocalDateTime.class) {
            return getLocalDateTimeBetween(builder, (Path<LocalDateTime>) path, value1, value2);
        }

        return builder.between((Path<String>) path, value1.toString(), value2.toString());
    }

    @Override
    public String getName() {
        return "BETWEEN";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        LocalDate dateFrom;
        LocalDate dateTo;
        Object value1 = values.get(0);
        Object value2 = values.get(1);
        try {
            dateFrom = LocalDate.parse((String) value1);
            dateTo = LocalDate.parse((String) value2);
        } catch (Exception e) {
            return "BETWEEN " + value1 + " AND " + value2;
        }
        return "BETWEEN '" + dateFrom + "' AND '" + dateTo + "'";
    }

}
