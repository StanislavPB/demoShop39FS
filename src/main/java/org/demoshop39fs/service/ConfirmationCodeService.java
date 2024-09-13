package org.demoshop39fs.service;

import lombok.RequiredArgsConstructor;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.NotFoundException;
import org.demoshop39fs.mapper.UserMapper;
import org.demoshop39fs.repository.ConfirmationCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository confirmationCodeRepository;


    public String createConfirmationCode(User user) {
        String newConfirmationCode = UUID.randomUUID().toString();

        ConfirmationCode code = ConfirmationCode.builder()
                .code(newConfirmationCode)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusDays(1))
                .build();

        confirmationCodeRepository.save(code);

        return newConfirmationCode;
    }

    public ConfirmationCode findByCode(String code){
        return confirmationCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Confirmation code not found or expired"));
    }

    public ConfirmationCode findByCodeExpireDateTimeAfter(String code, LocalDateTime date){
        return confirmationCodeRepository.findByCodeAndExpiredDateTimeAfter(code,date)
                .orElseThrow(() -> new NotFoundException("Confirmation code not found or expired"));
    }
}
