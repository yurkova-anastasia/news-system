package ru.clevertec.news_service.controller.response.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO class that represents a full news response, including its ID, header, content, publishedBy, and a list of CommentResponses.
 *
 * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
public class FullNewsResponse implements Serializable {

    @Schema(description = "ID of the news")
    private Long id;

    @Schema(description = "Header of the news")
    private String header;

    @Schema(description = "Content of the news")
    private String content;

    @Schema(description = "Publisher of the news")
    private String publishedBy;

    @Schema(description = "List of comments associated with the news")
    private List<CommentResponse> comments;
}
