package ru.clevertec.user_service.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityResponse {

    @Schema(description = "Name of the authority", requiredMode = Schema.RequiredMode.REQUIRED, example = "WRITE_COMMENTS")
    private String name;
}