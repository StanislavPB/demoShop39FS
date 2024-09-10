package org.demoshop39fs.service;

import lombok.Data;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.RestException;
import org.demoshop39fs.repository.ConfirmationCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Service
public class CreateConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    public String createCode(User user){
        String codeValue = UUID.randomUUID().toString();
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusDays(1))
                .build();

        repository.save(code);
        return codeValue;
    }


}
