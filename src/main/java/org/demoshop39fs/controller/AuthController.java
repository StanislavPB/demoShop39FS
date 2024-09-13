package org.demoshop39fs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demoshop39fs.controller.api.AuthApi;
import org.demoshop39fs.security.dto.AuthRequest;
import org.demoshop39fs.security.dto.AuthResponse;
import org.demoshop39fs.security.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        log.info("Attempting to authenticate user: " + request.getUserName());

        return ResponseEntity.ok(authService.authenticate(request));
    }

    @Override
    public ResponseEntity<AuthResponse> authenticateBasic(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(authService.authenticateBasic(authorizationHeader));
    }
}
