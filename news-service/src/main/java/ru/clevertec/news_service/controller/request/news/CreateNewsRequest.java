package ru.clevertec.news_service.controller.request.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class for creating news.
 *
 * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {

    @NotNull
    @Size(max = 100)
    @Schema(description = "Header of the news", required = true, maxLength = 100)
    private String header;

    @NotNull
    @Size(max = 2000)
    @Schema(description = "Content of the news", required = true, maxLength = 2000)
    private String content;

    @Schema(description = "Publisher of the news")
    private String publishedBy;

}
