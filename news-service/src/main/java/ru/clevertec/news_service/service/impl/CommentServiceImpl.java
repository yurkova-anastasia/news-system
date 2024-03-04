package ru.clevertec.news_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.logging.annotation.Logging;
import ru.clevertec.news_service.controller.request.comment.CreateCommentRequest;
import ru.clevertec.news_service.controller.request.comment.UpdateCommentRequest;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.entity.CommentEntity;
import ru.clevertec.news_service.exception.code.ServiceExceptionCode;
import ru.clevertec.news_service.exception.message.ServiceExceptionMessage;
import ru.clevertec.news_service.mapper.CommentMapper;
import ru.clevertec.news_service.repository.CommentRepository;
import ru.clevertec.news_service.service.CommentService;
import ru.clevertec.news_service.util.filter.FilterSpecification;
import ru.clevertec.news_service.util.search.FullTextSearchSpecification;

/**
 * A service implementation for managing {@link CommentEntity} data.
 */
@Logging
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentsRepository;
    private final CommentMapper commentMapper;
    private final FilterSpecification<CommentEntity> filterSpecification;
    private final FullTextSearchSpecification<CommentEntity> fullTextSearchSpecification;

    /**
     * Creates a new {@link CommentEntity} based on the given {@link CreateCommentRequest} data.
     *
     * @param createComment the request data containing the comment details
     * @return the created comment data, including its ID
     */
    @Override
    @Transactional
    @CachePut(cacheNames = "comments", key = "#result.id")
    public CommentResponse create(CreateCommentRequest createComment) {
        CommentEntity comment = commentMapper.toEntity(createComment);
        return commentMapper.toResponse(commentsRepository.save(comment));
    }

    /**
     * Finds all comments that match the given search query and filter criteria.
     *
     * @param search  the search query
     * @param filter  the filter criteria
     * @param pageable the pagination information
     * @return a page of comments that match the search and filter criteria
     */
    @Override
    public Page<CommentResponse> findAll(String search, String filter, Pageable pageable) {
        Specification<CommentEntity> filterSpec = filterSpecification.getSpecification(filter);
        Specification<CommentEntity> fullTextSpec = fullTextSearchSpecification.getSpecification(search);
        return commentsRepository.findAll(filterSpec.and(fullTextSpec), pageable)
                .map(commentMapper::toResponse);
    }

    /**
     * Finds the comment with the given ID.
     *
     * @param id the ID of the comment to find
     * @return the comment with the given ID
     */
    @Override
    @Cacheable(cacheNames = "comments", key = "#id")
    public CommentResponse findById(Long id) {
        return commentMapper.toResponse(getById(id));
    }

    /**
     * Updates the comment with the given ID based on the given {@link UpdateCommentRequest} data.
     *
     * @param id           the ID of the comment to update
     * @param updateComment the request data containing the updated comment details
     * @return the updated comment data
     */
    @Override
    @Transactional
    @CachePut(cacheNames = "comments", key = "#id")
    public CommentResponse update(Long id, UpdateCommentRequest updateComment) {
        CommentEntity comment = getById(id);
        updatePartially(comment, updateComment);
        return commentMapper.toResponse(commentsRepository.save(comment));
    }

    /**
     * Deletes the comment with the given ID.
     *
     * @param id the ID of the comment to delete
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "comments", key="#id")
    public void delete(Long id) {
        commentsRepository.deleteById(id);
    }

    /**
     * Returns the comment with the given ID.
     *
     * @param id the ID of the comment to find
     * @return the comment with the given ID
     */
    private CommentEntity getById(Long id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ServiceExceptionCode.COMMENT_NOT_FOUND,
                        ServiceExceptionMessage.COMMENT_NOT_FOUND));
    }

    /**
     * Updates the given comment with the given updated data.
     *
     * @param comment      the comment to update
     * @param updateComment the updated data
     */
    private void updatePartially(CommentEntity comment, UpdateCommentRequest updateComment) {
        if (updateComment.getHeader() != null) {
            comment.setHeader(updateComment.getHeader());
        }
        if (updateComment.getContent() != null) {
            comment.setContent(updateComment.getContent());
        }
    }

}
