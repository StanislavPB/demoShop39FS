package org.demoshop39fs.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demoshop39fs.security.dto.AuthRequest;
import org.demoshop39fs.security.dto.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    // Метод для аутентификации по DTO запросу
    public AuthResponse authenticate(AuthRequest request) {

        return authenticateUser(request.getUserName(), request.getPassword());

    }


    // Метод для аутентификации по Basic Auth
    public AuthResponse authenticateBasic(String authHeader) {

        // Проверяем корректность заголовка Authorization

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new IllegalArgumentException("Invalid Basic authentication token");
        }
        // Декодируем и парсим заголовок
        String[] credentials = decodingBasicAuth(authHeader);

        return authenticateUser(credentials[0], credentials[1]);

    }

    private String[] decodingBasicAuth(String authHeader) {


        // "Basic dHJVJGUncvjgJVJ=="
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);
        // Username:..... password:.....
        String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid Basic authentication token");

        }

        return values;

    }

    private AuthResponse authenticateUser(String username, String password) {

        log.info("Authenticate user: " + username);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Запрос на создание jwt от " + username + ", " + password);

            String jwt = tokenProvider.createToken(authentication.getName());

            return new AuthResponse(jwt);
        } catch (Exception e) {
            log.error("Authentication failed for user: " + username, e);
            throw e;
            }


    }


}
