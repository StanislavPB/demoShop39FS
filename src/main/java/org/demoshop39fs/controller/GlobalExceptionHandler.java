package org.demoshop39fs.controller;

import org.demoshop39fs.dto.ErrorResponseDto;
import org.demoshop39fs.exceptions.AlreadyExistException;
import org.demoshop39fs.exceptions.NotFoundException;
import org.demoshop39fs.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status, String errorType) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(message)
                .statusCode(status.value())
                .errorType(errorType)
                .build();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, "NotFoundException");
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExistException(AlreadyExistException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "AlreadyExistException");
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponseDto> handleRestException(RestException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT, "RestException");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder responseMessage = new StringBuilder();
        e.getConstraintViolations().forEach(constraintViolation -> {
            responseMessage.append(constraintViolation.getMessage()).append("\n");
        });

        return buildErrorResponse(responseMessage.toString(), HttpStatus.BAD_REQUEST, "ConstraintViolationException");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception e) {
        return buildErrorResponse("Произошла ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "GeneralException");
    }
}
