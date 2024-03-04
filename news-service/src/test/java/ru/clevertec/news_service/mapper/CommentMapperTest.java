package ru.clevertec.news_service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.entity.CommentEntity;
import ru.clevertec.news_service.entity.NewsEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        this.commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @Test
    void toResponse_shouldTransferCommentObjectToCommentResponse() {
        NewsEntity news = NewsEntity.builder()
                .id(5L)
                .build();
        CommentEntity comment = CommentEntity.builder()
                .id(1L)
                .header("header")
                .content("content")
                .publishedBy("user")
                .news(news)
                .build();
        CommentResponse expected = CommentResponse.builder()
                .id(1L)
                .header("header")
                .content("content")
                .publishedBy("user")
                .newsId(news.getId())
                .build();

        CommentResponse actual = commentMapper.toResponse(comment);

        assertEquals(expected, actual);
    }

    @Test
    void toEntity_shouldTransferCreateCommentRequestObjectToCommentEntity() {
        CreateCommentRequest commentRequest = CreateCommentRequest.builder()
                .header("header")
                .content("content")
                .publishedBy("user")
                .newsId(1L)
                .build();
        NewsEntity news = new NewsEntity();
        news.setId(1L);

        CommentEntity actual = commentMapper.toEntity(commentRequest);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("header", commentRequest.getHeader())
                .hasFieldOrPropertyWithValue("content", commentRequest.getContent())
                .hasFieldOrPropertyWithValue("publishedBy", commentRequest.getPublishedBy())
                .hasFieldOrPropertyWithValue("news.id", news.getId());
    }


}