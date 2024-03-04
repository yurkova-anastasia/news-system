package ru.clevertec.news_service.controller.request.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class for updating news.
 *
 * @author Yurkova Anastasia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsRequest {

    @Size(max = 100)
    @Schema(description = "Header of the news", maxLength = 100)
    private String header;

    @Size(max = 2000)
    @Schema(description = "Content of the news", maxLength = 2000)
    private String content;

}
