package ru.clevertec.news_service.controller.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class for creating a comment.
 *
 * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    @NotNull
    @Schema(description = "Header of the comment", required = true, maxLength = 100)
    private String header;

    @NotNull
    @Size(max = 2000)
    @Schema(description = "Content of the comment", required = true, maxLength = 2000)
    private String content;

    @NotNull
    @Schema(description = "Publisher of the comment", required = true)
    private String publishedBy;

    @NotNull
    @Schema(description = "ID of the news associated with the comment", required = true)
    private Long newsId;

}
