package org.demoshop39fs.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.demoshop39fs.dto.CreateRequestUser;
import org.demoshop39fs.dto.StandardResponseDto;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public interface PublicApi {

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register")
    ResponseEntity<UserResponse> registerUser(@Valid @RequestBody CreateRequestUser request);


    @Operation(summary = "Подтверждение регистрации пользователя по коду подтверждения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно подтвержден",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный код подтверждения",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/confirm")
    ResponseEntity<StandardResponseDto> confirmUser(@RequestParam String confirmationCode);
}
