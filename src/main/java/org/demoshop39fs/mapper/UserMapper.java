package org.demoshop39fs.mapper;

import org.demoshop39fs.dto.*;
import org.demoshop39fs.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Преобразование CreateRequestUser в сущность User
    @Mapping(target = "id", ignore = true)  // ID генерируется базой данных
    @Mapping(target = "role", expression = "java(User.Role.USER)")  // Устанавливаем роль по умолчанию
    @Mapping(target = "state", expression = "java(User.State.NOT_CONFIRMED)")  // Состояние по умолчанию
    User toEntity(CreateRequestUser createRequestUser);

    // Преобразование сущности User в ответ DTO
    UserResponse toResponse(User user);

    // Преобразование списка пользователей в список ответов DTO
    List<UserResponse> toResponseList(List<User> users);

    // Обновление сущности User на основе DTO UpdateUserRequest (для обновления пользователем)
    @Mapping(source = "updateUserRequest.firstName", target = "firstName")
    @Mapping(source = "updateUserRequest.lastName", target = "lastName")
    @Mapping(source = "updateUserRequest.photoLink", target = "photoLink")
    @Mapping(source = "updateUserRequest.password", target = "password")
    @Mapping(target = "id", ignore = true)
    User updateUserFromDto(UpdateUserRequest updateUserRequest, User user);

    // Обновление сущности User на основе DTO UpdateUserRequestForAdmin (для обновления администратором)
    @Mapping(source = "updateUserRequestForAdmin.firstName", target = "firstName")
    @Mapping(source = "updateUserRequestForAdmin.lastName", target = "lastName")
    @Mapping(source = "updateUserRequestForAdmin.photoLink", target = "photoLink")
    @Mapping(source = "updateUserRequestForAdmin.password", target = "password")
    @Mapping(source = "updateUserRequestForAdmin.role", target = "role", qualifiedByName = "stringToRole")
    @Mapping(source = "updateUserRequestForAdmin.state", target = "state", qualifiedByName = "stringToState")
    @Mapping(target = "id", ignore = true)
    User updateUserFromAdminDto(UpdateUserRequestForAdmin updateUserRequestForAdmin, User user);

    // Преобразование строки в enum Role
    @Named("stringToRole")
    default User.Role stringToRole(String role) {
        return User.Role.valueOf(role.toUpperCase());  // Преобразуем строку в значение перечисления Role
    }

    // Преобразование строки в enum State
    @Named("stringToState")
    default User.State stringToState(String state) {
        return User.State.valueOf(state.toUpperCase());  // Преобразуем строку в значение перечисления State
    }
}
