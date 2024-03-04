package ru.clevertec.news_service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.entity.CommentEntity;
import ru.clevertec.news_service.entity.NewsEntity;

/**
 * A mapper for converting between CommentEntity and CommentResponse objects.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.FIELD)
public interface CommentMapper {

    /**
     * Maps a CreateCommentRequest to a CommentEntity.
     *
     * @param createComment the CreateCommentRequest to map
     * @return the resulting CommentEntity
     */
    @Mapping(target = "news", source = "newsId", qualifiedByName = "newsId")
    CommentEntity toEntity(CreateCommentRequest createComment);

    /**
     * Maps a CommentEntity to a CommentResponse.
     *
     * @param comment the CommentEntity to map
     * @return the resulting CommentResponse
     */
    @Mapping(target = "newsId", source = "news", qualifiedByName = "news")
    CommentResponse toResponse(CommentEntity comment);

    @Named("newsId")
    default NewsEntity getNews(Long newsId) {
        NewsEntity news = new NewsEntity();
        news.setId(newsId);
        return news;
    }

    @Named("news")
    default Long getNewsId(NewsEntity news) {
        return news.getId();
    }
}
