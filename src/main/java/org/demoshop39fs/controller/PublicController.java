package org.demoshop39fs.controller;

import org.demoshop39fs.controller.api.PublicApi;
import org.demoshop39fs.dto.CreateRequestUser;
import org.demoshop39fs.dto.StandardResponseDto;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.service.ConfirmationCodeService;
import org.demoshop39fs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController implements PublicApi {

    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    public PublicController(UserService userService, ConfirmationCodeService confirmationCodeService) {
        this.userService = userService;
        this.confirmationCodeService = confirmationCodeService;
    }

    @Override
    public ResponseEntity<UserResponse> registerUser(CreateRequestUser request) {
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<StandardResponseDto> confirmUser(String confirmationCode) {
        return ResponseEntity.ok(userService.confirmUser(confirmationCode));
    }
}
