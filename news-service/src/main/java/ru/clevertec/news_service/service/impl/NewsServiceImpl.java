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
import ru.clevertec.cachestarter.annotation.CustomCacheable;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.logging.annotation.Logging;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.request.news.UpdateNewsRequest;
import ru.clevertec.news_service.controller.response.news.FullNewsResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.entity.NewsEntity;
import ru.clevertec.news_service.exception.code.ServiceExceptionCode;
import ru.clevertec.news_service.exception.message.ServiceExceptionMessage;
import ru.clevertec.news_service.mapper.NewsMapper;
import ru.clevertec.news_service.repository.NewsRepository;
import ru.clevertec.news_service.service.NewsService;
import ru.clevertec.news_service.util.filter.FilterSpecification;
import ru.clevertec.news_service.util.search.FullTextSearchSpecification;

/**
 * A service implementation for managing news.
 */
@Logging
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final FilterSpecification<NewsEntity> filterSpecification;
    private final FullTextSearchSpecification<NewsEntity> fullTextSearchSpecification;

    /**
     * Creates a new news item.
     *
     * @param createNews the news item data
     * @return the created news item
     */
    @Override
    @CachePut(cacheNames = "news", key = "#result.id")
    @CustomCacheable
    @Transactional
    public NewsResponse create(CreateNewsRequest createNews) {
        NewsEntity news = newsMapper.toEntity(createNews);
        return newsMapper.toResponse(newsRepository.save(news));
    }

    /**
     * Finds all news items.
     *
     * @param search    the search query
     * @param filter    the filter criteria
     * @param pageable  the pagination information
     * @return a page of news items
     */
    @Override
    public Page<NewsResponse> findAll(String search, String filter, Pageable pageable) {
        Specification<NewsEntity> filterSpec = filterSpecification.getSpecification(filter);
        Specification<NewsEntity> fullTextSpec = fullTextSearchSpecification.getSpecification(search);
        return newsRepository.findAll(filterSpec.and(fullTextSpec), pageable)
                .map(newsMapper::toResponse);
    }

    /**
     * Finds a news item by ID.
     *
     * @param id the ID of the news item
     * @return the news item with the given ID
     */
    @Override
    @Cacheable(cacheNames = "comments", key = "#id")
    @CustomCacheable
    public NewsResponse findById(Long id) {
        return newsMapper.toResponse(getById(id));
    }

    /**
     * Finds a news item by ID, including its comments.
     *
     * @param id the ID of the news item
     * @return the news item with the given ID, including its comments
     */
    @Override
    @Cacheable(cacheNames = "fullNews", key = "#id")
    public FullNewsResponse findNewsWithComments(Long id) {
        return newsMapper.toFullNewsResponse(getById(id));
    }

    /**
     * Updates an existing news item.
     *
     * @param id        the ID of the news item
     * @param updateNews the updated news item data
     * @return the updated news item
     */
    @Override
    @CachePut(cacheNames = "news", key = "#id")
    @CustomCacheable
    @Transactional
    public NewsResponse update(Long id, UpdateNewsRequest updateNews) {
        NewsEntity news = getById(id);
        updatePartially(news, updateNews);
        return newsMapper.toResponse(newsRepository.save(news));
    }

    /**
     * Deletes an existing news item.
     *
     * @param id the ID of the news item
     */
    @Override
    @CacheEvict(cacheNames = "news", key = "#id")
    @CustomCacheable
    @Transactional
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    /**
     * Returns a news item with the given ID.
     *
     * @param id the ID of the news item
     * @return the news item with the given ID
     */
    private NewsEntity getById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ServiceExceptionCode.NEWS_NOT_FOUND,
                        ServiceExceptionMessage.NEWS_NOT_FOUND));
    }

    /**
     * Updates the given news item with the given update data.
     *
     * @param news      the news item to update
     * @param updateNews the update data
     */
    private void updatePartially(NewsEntity news, UpdateNewsRequest updateNews) {
        if (updateNews.getHeader() != null) {
            news.setHeader(updateNews.getHeader());
        }
        if (updateNews.getContent() != null) {
            news.setContent(updateNews.getContent());
        }
    }
}
