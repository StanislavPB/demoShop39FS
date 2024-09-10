package org.demoshop39fs.controller;

import lombok.RequiredArgsConstructor;
import org.demoshop39fs.controller.api.AuthApi;
import org.demoshop39fs.security.dto.AuthRequest;
import org.demoshop39fs.security.dto.AuthResponse;
import org.demoshop39fs.security.service.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication.getName());

        return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
    }
}
