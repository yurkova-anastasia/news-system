package ru.clevertec.news_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.request.news.UpdateNewsRequest;
import ru.clevertec.news_service.controller.response.news.FullNewsResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;

public interface NewsService {

    NewsResponse create(CreateNewsRequest createNews);

    Page<NewsResponse> findAll(String search, String filter, Pageable pageable);

    NewsResponse findById(Long id);

    FullNewsResponse findNewsWithComments(Long id);

    NewsResponse update(Long id, UpdateNewsRequest updateNews);

    void delete(Long id);

}
