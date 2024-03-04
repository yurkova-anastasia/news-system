package ru.clevertec.news_service.controller.response.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * NewsResponse class represents a response for News.
 * It contains the News's id, header, content, and publishedBy.
 *
  * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse implements Serializable {

    @Schema(description = "ID of the news")
    private Long id;

    @Schema(description = "Header of the news")
    private String header;

    @Schema(description = "Content of the news")
    private String content;

    @Schema(description = "Publisher of the news")
    private String publishedBy;

}
