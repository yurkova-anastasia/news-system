package ru.clevertec.news_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.clevertec.news_service.entity.metadata.AuditEntity_;

import java.time.LocalDateTime;

/**
 * A base class for auditing entities.
 *
 * @author Yurkova Anastasia
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {

    /**
     * The date and time the entity was created.
     */
    @CreatedDate
    @Column(name = AuditEntity_.CREATED_AT, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time the entity was last updated.
     */
    @LastModifiedDate
    @Column(name = AuditEntity_.UPDATED_AT, insertable = false)
    private LocalDateTime updatedAt;

}