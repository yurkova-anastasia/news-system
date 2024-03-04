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
public class LessThanPredicate<T> implements PredicateGenerator<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        Class<?> javaType = criteria.getJavaType();
        Object value = criteria.getSearchUnitValues().get(0);
        Path<Object> path = getPath(root, criteria);

        return getLessThanPredicate(builder, javaType, path, value);
    }

    @SuppressWarnings("unchecked")
    private Predicate getLessThanPredicate(CriteriaBuilder builder, Class<?> javaType, Path<?> path, Object value) {
        if (javaType == LocalDate.class) {
            return builder.lessThan((Path<LocalDate>) path, (LocalDate) value);
        }
        if (javaType == LocalDateTime.class) {
            return builder.lessThan((Path<LocalDateTime>) path, (LocalDateTime) value);
        }
        return builder.lessThan((Path<String>) path, value.toString());
    }

    @Override
    public String getName() {
        return "LESS_THAN";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        LocalDate date;
        try {
            date = LocalDate.parse((String) values.get(0));
        } catch (Exception e) {
            return "< " + values.get(0);
        }
        return "< '" + date + "'";
    }

}
