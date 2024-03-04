package ru.clevertec.news_service.util.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FullTextSearchSpecification<T> implements Specification<T> {

    private final FullTextSearchParser fullTextSearchParser;
    private String search;

    public Specification<T> getSpecification(String search) {
        if (search == null) {
            return Specification.where(null);
        }

        this.search = fullTextSearchParser.parse(search);
        return this;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<T> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder builder
    ) {
        Expression<String> languageExpression = builder.literal("russian");
        Expression<String> queryExpression = builder.literal(search);

        Expression<Boolean> searchExpression = builder.function(
                "search @@ to_tsquery",
                Boolean.class,
                languageExpression,
                queryExpression
        );

        return builder.isTrue(searchExpression);
    }

}
