package ru.clevertec.news_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ru.clevertec.news_service.entity.metadata.CommentEntity_;

/**
 * CommentEntity class represents a comment to a news article.
 * It is an entity in the database, so it has an ID, header, content, and publishedBy fields.
 * It also has a many-to-one relationship with a NewsEntity, which represents the news article it is commenting on.
 *
 * @author Yurkova Anastasia
 */
@Entity
@Table(name = CommentEntity_.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Builder
@Accessors(chain = true)
public class CommentEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CommentEntity_.ID)
    private Long id;

    @Column(name = CommentEntity_.HEADER)
    private String header;

    @Column(name = CommentEntity_.CONTENT)
    private String content;

    @Column(name = CommentEntity_.PUBLISHED_BY)
    private String publishedBy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private NewsEntity news;

}
