package ru.clevertec.news_service.entity.metadata;

import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.news_service.entity.AuditEntity;

@StaticMetamodel(value = AuditEntity.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentEntity_ {

    public static final String TABLE = "comments";
    public static final String ID = "id";
    public static final String HEADER = "header";
    public static final String CONTENT = "content";
    public static final String PUBLISHED_BY = "published_by";

}
