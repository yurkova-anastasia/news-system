package ru.clevertec.news_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import ru.clevertec.news_service.entity.metadata.NewsEntity_;

import java.util.List;

/**
 * NewsEntity class represents news.
 * It is an entity in the database, so it has an ID, header, content, and publishedBy fields.
 * It also has a one-to-many relationship with a CommentEntity.
 *
 * @author Yurkova Anastasia
 */
@Entity
@Table(name = NewsEntity_.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Builder
@Accessors(chain = true)
public class NewsEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = NewsEntity_.ID)
    private Long id;

    @Column(name = NewsEntity_.HEADER)
    private String header;

    @Column(name = NewsEntity_.CONTENT)
    private String content;

    @Column(name = NewsEntity_.PUBLISHED_BY)
    private String publishedBy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

}
