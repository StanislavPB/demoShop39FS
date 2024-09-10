package org.demoshop39fs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    @Schema(description = "Сообщение об ошибке", example = "Пользователь не найден")
    private String message;

    @Schema(description = "Код HTTP-ответа", example = "404")
    private int statusCode;

    @Schema(description = "Тип ошибки", example = "NotFoundException")
    private String errorType;
}
