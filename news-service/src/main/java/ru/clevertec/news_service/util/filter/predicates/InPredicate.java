package ru.clevertec.news_service.util.filter.predicates;

import ru.clevertec.news_service.util.filter.dto.SearchCriteriaExpanded;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class InPredicate<T> implements PredicateGenerator<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder builder, SearchCriteriaExpanded criteria) {
        List<Object> value = criteria.getSearchUnitValues();
        Path<Object> path = getPath(root, criteria);

        return builder.in(path).value(value);
    }

    @Override
    public String getName() {
        return "IN";
    }

    @Override
    public String toSql(List<Object> values, String key) {
        return "IN " + convertListToString(values);
    }

    public String convertListToString(List<Object> list) {
        StringBuilder result = new StringBuilder("(");

        for (int i = 0; i < list.size(); i++) {
            final String stringValue = String.valueOf(list.get(i));
            if (stringValue.matches("((0|[1-9]\\d*|\\d*\\.\\d+)|true|false)$")) {
                result.append(list.get(i));
            } else {
                result.append("'").append(list.get(i)).append("'");
            }

            if (i < list.size() - 1) {
                result.append(", ");
            }
        }

        result.append(")");

        return result.toString();
    }

}
