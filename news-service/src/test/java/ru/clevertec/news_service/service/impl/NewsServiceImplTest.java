package ru.clevertec.news_service.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.request.comment.UpdateCommentRequest;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.request.news.UpdateNewsRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.entity.NewsEntity;
import ru.clevertec.news_service.entity.NewsEntity;
import ru.clevertec.news_service.mapper.NewsMapper;
import ru.clevertec.news_service.repository.NewsRepository;
import ru.clevertec.news_service.util.filter.FilterSpecification;
import ru.clevertec.news_service.util.search.FullTextSearchSpecification;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {
    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private FilterSpecification<NewsEntity> filterSpecification;

    @Mock
    private FullTextSearchSpecification<NewsEntity> fullTextSearchSpecification;

    @Test
    void whenCreateComment_thenReturnCommentResponse() {
        CreateNewsRequest createNewsRequest = new CreateNewsRequest();
        NewsEntity newsEntity = new NewsEntity();
        NewsResponse newsResponse = new NewsResponse();
        given(newsMapper.toEntity(createNewsRequest)).willReturn(newsEntity);
        given(newsRepository.save(newsEntity)).willReturn(newsEntity);
        given(newsMapper.toResponse(newsEntity)).willReturn(newsResponse);

        NewsResponse result = newsService.create(createNewsRequest);

        assertThat(result).isEqualTo(newsResponse);
    }

    @Test
    void whenFindAllComments_thenReturnPageOfCommentResponse() {
        String search = "test";
        String filter = "someFilter";
        Pageable pageable = PageRequest.of(0, 10);
        Specification<NewsEntity> filterSpec = mock(Specification.class);
        Specification<NewsEntity> fullTextSpec = mock(Specification.class);
        Page<NewsEntity> page = new PageImpl<>(Collections.emptyList());
        when(filterSpecification.getSpecification(filter)).thenReturn(filterSpec);
        when(fullTextSearchSpecification.getSpecification(search)).thenReturn(fullTextSpec);
        when(newsRepository.findAll(filterSpec.and(fullTextSpec), pageable)).thenReturn(page);

        Page<NewsResponse> resultPage = newsService.findAll(search, filter, pageable);

        verify(newsRepository, times(1)).findAll(filterSpec.and(fullTextSpec), pageable);
        assertEquals(page.getTotalElements(), resultPage.getTotalElements());
    }

    @Test
    void findById_WhenCalledWithExistingId_ShouldReturnComment() {
        Long id = 1L;
        NewsEntity newsEntity = NewsEntity.builder()
                .id(id)
                .build();
        NewsResponse newsResponse = NewsResponse.builder()
                .id(id)
                .build();
        when(newsRepository.findById(id)).thenReturn(Optional.of(newsEntity));
        when(newsMapper.toResponse(newsEntity)).thenReturn(newsResponse);

        NewsResponse response = newsService.findById(id);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
    }


    @Test
    void update_whenCalled_thenUpdateNews() {
        // Arrange
        Long id = 1L;
        String existingHeader = "Existing Header";
        String existingContent = "Existing Content";
        String updatedHeader = "Updated Header";
        UpdateNewsRequest updateNewsRequest = new UpdateNewsRequest();
        updateNewsRequest.setHeader(updatedHeader);
        NewsEntity news = NewsEntity.builder()
                .id(id)
                .header(existingHeader)
                .content(existingContent)
                .build();
        when(newsRepository.findById(id)).thenReturn(Optional.of(news));
        news.setHeader(updatedHeader);
        when(newsRepository.save(news)).thenReturn(news);
        NewsResponse expected = NewsResponse.builder()
                .id(id)
                .header(updatedHeader)
                .content(existingContent)
                .build();
        when(newsMapper.toResponse(news)).thenReturn(expected);

        NewsResponse actual = newsService.update(id, updateNewsRequest);

        assertEquals(expected.getHeader(), actual.getHeader());
        assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    void delete_whenCalled_thenDeleteNewsById() {

        Long id = 1L;

        newsService.delete(id);

        verify(newsRepository).deleteById(id);
    }
}