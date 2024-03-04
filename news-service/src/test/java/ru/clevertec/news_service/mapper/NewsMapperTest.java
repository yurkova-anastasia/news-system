package ru.clevertec.news_service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.entity.NewsEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewsMapperTest {

    private NewsMapper newsMapper;

    @BeforeEach
    void setUp() {
        this.newsMapper = Mappers.getMapper(NewsMapper.class);
    }

    @Test
    void toResponse_shouldTransferCommentObjectToCommentResponse() {
        NewsResponse expected = NewsResponse.builder()
                .id(1L)
                .header("header")
                .content("content")
                .publishedBy("user")
                .build();
        NewsEntity news = NewsEntity.builder()
                .id(1L)
                .header("header")
                .content("content")
                .publishedBy("user")
                .build();

        NewsResponse actual = newsMapper.toResponse(news);

        assertEquals(expected, actual);
    }

    @Test
    void toEntity_shouldTransferCreateCommentRequestObjectToCommentEntity() {
        CreateNewsRequest newsRequest = CreateNewsRequest.builder()
                .header("header")
                .content("content")
                .publishedBy("user")
                .build();

        NewsEntity actual = newsMapper.toEntity(newsRequest);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("header", newsRequest.getHeader())
                .hasFieldOrPropertyWithValue("content", newsRequest.getContent())
                .hasFieldOrPropertyWithValue("publishedBy", newsRequest.getPublishedBy());
    }

}