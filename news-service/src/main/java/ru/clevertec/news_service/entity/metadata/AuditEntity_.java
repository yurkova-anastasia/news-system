package ru.clevertec.news_service.entity.metadata;

import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.news_service.entity.AuditEntity;

@StaticMetamodel(value = AuditEntity.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditEntity_ {

    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

}
