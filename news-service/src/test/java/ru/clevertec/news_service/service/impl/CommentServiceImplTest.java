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
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.entity.CommentEntity;
import ru.clevertec.news_service.mapper.CommentMapper;
import ru.clevertec.news_service.repository.CommentRepository;
import ru.clevertec.news_service.util.filter.FilterSpecification;
import ru.clevertec.news_service.util.search.FullTextSearchSpecification;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private FilterSpecification<CommentEntity> filterSpecification;

    @Mock
    private FullTextSearchSpecification<CommentEntity> fullTextSearchSpecification;

    @Test
    void whenCreateComment_thenReturnCommentResponse() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        CommentEntity commentEntity = new CommentEntity();
        CommentResponse commentResponse = new CommentResponse();
        given(commentMapper.toEntity(createCommentRequest)).willReturn(commentEntity);
        given(commentRepository.save(commentEntity)).willReturn(commentEntity);
        given(commentMapper.toResponse(commentEntity)).willReturn(commentResponse);

        CommentResponse result = commentService.create(createCommentRequest);

        assertThat(result).isEqualTo(commentResponse);
    }

    @Test
    void whenFindAllComments_thenReturnPageOfCommentResponse() {
        String search = "test";
        String filter = "someFilter";
        Pageable pageable = PageRequest.of(0, 10);
        Specification<CommentEntity> filterSpec = mock(Specification.class);
        Specification<CommentEntity> fullTextSpec = mock(Specification.class);
        Page<CommentEntity> page = new PageImpl<>(Collections.emptyList());
        when(filterSpecification.getSpecification(filter)).thenReturn(filterSpec);
        when(fullTextSearchSpecification.getSpecification(search)).thenReturn(fullTextSpec);
        when(commentRepository.findAll(filterSpec.and(fullTextSpec), pageable)).thenReturn(page);

        Page<CommentResponse> resultPage = commentService.findAll(search, filter, pageable);

        verify(commentRepository, times(1)).findAll(filterSpec.and(fullTextSpec), pageable);
        assertEquals(page.getTotalElements(), resultPage.getTotalElements());
    }

    @Test
    void findById_WhenCalledWithExistingId_ShouldReturnComment() {
        Long id = 1L;
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .build();
        CommentResponse commentResponse = CommentResponse.builder()
                .id(id)
                .build();
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));
        when(commentMapper.toResponse(commentEntity)).thenReturn(commentResponse);

        CommentResponse response = commentService.findById(id);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
    }


    @Test
    void update_whenCalled_thenUpdateComment() {
        // Arrange
        Long id = 1L;
        String existingHeader = "Existing Header";
        String existingContent = "Existing Content";
        String updatedHeader = "Updated Header";
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest();
        updateCommentRequest.setHeader(updatedHeader);
        CommentEntity comment = CommentEntity.builder()
                .id(id)
                .header(existingHeader)
                .content(existingContent)
                .build();
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        comment.setHeader(updatedHeader);
        when(commentRepository.save(comment)).thenReturn(comment);
        CommentResponse expected = CommentResponse.builder()
                .id(id)
                .header(updatedHeader)
                .content(existingContent)
                .build();
        when(commentMapper.toResponse(comment)).thenReturn(expected);

        CommentResponse actual = commentService.update(id, updateCommentRequest);

        assertEquals(expected.getHeader(), actual.getHeader());
        assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    void delete_whenCalled_thenDeleteCommentById() {

        Long id = 1L;

        commentService.delete(id);

        verify(commentRepository).deleteById(id);
    }


}