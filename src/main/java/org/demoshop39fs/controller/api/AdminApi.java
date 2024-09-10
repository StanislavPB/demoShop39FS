package org.demoshop39fs.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.demoshop39fs.dto.UpdateUserRequestForAdmin;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public interface AdminApi {

    @Operation(summary = "Обновление пользователя администратором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    ResponseEntity<UserResponse> updateUserForAdmin(@Valid @RequestBody UpdateUserRequestForAdmin request);


    @Operation(summary = "Поиск пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/find/{id}")
    ResponseEntity<UserResponse> findUserById(@PathVariable Integer id);


    @Operation(summary = "Поиск пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/find")
    ResponseEntity<UserResponse> findUserById(@RequestParam String email);


    @Operation(summary = "Поиск пользователей по фамилии")
    @ApiResponse(responseCode = "200", description = "Список пользователей найден",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/findByLastName")
    ResponseEntity<List<UserResponse>> findUserByLastName(@RequestParam String lastName);


    @Operation(summary = "Поиск пользователей по имени и фамилии")
    @ApiResponse(responseCode = "200", description = "Список пользователей найден",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/findByFullName")
    ResponseEntity<List<UserResponse>> findUserByFullName(@RequestParam String firstName,
                                                          @RequestParam String lastName);
}
