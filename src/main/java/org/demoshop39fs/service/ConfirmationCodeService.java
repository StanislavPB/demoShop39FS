package org.demoshop39fs.service;

import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.NotFoundException;
import org.demoshop39fs.mapper.UserMapper;
import org.demoshop39fs.repository.ConfirmationCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public ConfirmationCodeService(ConfirmationCodeRepository confirmationCodeRepository, UserService userService, UserMapper userMapper) {
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserResponse confirmUser(String confirmationCode) {
        ConfirmationCode code = confirmationCodeRepository.findByCodeAndExpiredDateTimeAfter(confirmationCode, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Confirmation code not found or expired"));

        User user = code.getUser();
        user.setState(User.State.CONFIRMED);
        userService.updatePhotoLink(user.getId(), user.getPhotoLink());  // Если нужно обновить photoLink
        return userMapper.toResponse(user);
    }
}
