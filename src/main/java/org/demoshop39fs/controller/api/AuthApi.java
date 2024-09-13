package org.demoshop39fs.controller.api;

import jakarta.validation.Valid;
import org.demoshop39fs.security.dto.AuthRequest;
import org.demoshop39fs.security.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping
    ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request);


    @PostMapping("/header")
    ResponseEntity<AuthResponse> authenticateBasic(@RequestHeader(value = "Authorization") String authorizationHeader);
}
