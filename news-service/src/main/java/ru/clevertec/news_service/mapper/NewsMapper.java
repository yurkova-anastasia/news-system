package ru.clevertec.news_service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.news_service.controller.request.news.CreateNewsRequest;
import ru.clevertec.news_service.controller.response.news.FullNewsResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.entity.NewsEntity;

/**
 * A mapper for converting between the {@link NewsEntity} and the {@link NewsResponse} objects.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.FIELD)
public interface NewsMapper {

    /**
     * Maps the {@link CreateNewsRequest} to the {@link NewsEntity}.
     *
     * @param createNews the news data to map
     * @return the news entity
     */
    NewsEntity toEntity(CreateNewsRequest createNews);

    /**
     * Maps the {@link NewsEntity} to the {@link NewsResponse}.
     *
     * @param news the news entity to map
     * @return the news response
     */
    NewsResponse toResponse(NewsEntity news);

    /**
     * Maps the {@link NewsEntity} to the {@link FullNewsResponse}.
     *
     * @param news the news entity to map
     * @return the full news response
     */
    FullNewsResponse toFullNewsResponse(NewsEntity news);
}
