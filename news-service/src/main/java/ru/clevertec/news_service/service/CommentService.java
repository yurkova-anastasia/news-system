package ru.clevertec.news_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.request.comment.UpdateCommentRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;

public interface CommentService {

    CommentResponse create(CreateCommentRequest createComment);

    Page<CommentResponse> findAll(String search, String filter, Pageable pageable);

    CommentResponse findById(Long id);

    CommentResponse update(Long id, UpdateCommentRequest updateComment);

    void delete(Long id);
}
