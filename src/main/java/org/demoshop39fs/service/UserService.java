package org.demoshop39fs.service;

import org.demoshop39fs.dto.*;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.NotFoundException;
import org.demoshop39fs.mapper.UserMapper;
import org.demoshop39fs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse registerUser(CreateRequestUser request) {
        User user = userMapper.toEntity(request);
        user.setRole(User.Role.USER);
        user.setState(User.State.NOT_CONFIRMED);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        userMapper.updateUserFromDto(request, user);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse updateUserForAdmin(UpdateUserRequestForAdmin request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        userMapper.updateUserFromAdminDto(request, user);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<UserResponse> getUsersByLastName(String lastName) {
        return userRepository.findByLastName(lastName)
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByFullName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Использование нового метода для обновления photoLink
    public void updatePhotoLink(Integer userId, String photoLink) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found");
        }
        userRepository.updatePhotoLinkById(userId, photoLink);
    }
}
