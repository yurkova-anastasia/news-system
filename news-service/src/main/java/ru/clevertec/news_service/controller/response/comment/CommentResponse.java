package ru.clevertec.news_service.controller.response.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CommentResponse class represents a response for a Comment.
 * It contains the Comment's id, header, content, and publishedBy.
 *
 * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse implements Serializable {

    @Schema(description = "ID of the comment")
    private Long id;

    @Schema(description = "Header of the comment")
    private String header;

    @Schema(description = "Content of the comment")
    private String content;

    @Schema(description = "Publisher of the comment")
    private String publishedBy;

    @Schema(description = "ID of the news associated with the comment")
    private Long newsId;

}
