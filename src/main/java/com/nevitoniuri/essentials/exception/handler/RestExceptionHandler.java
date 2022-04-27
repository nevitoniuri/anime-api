package com.nevitoniuri.essentials.exception.handler;

import com.nevitoniuri.essentials.exception.BadRequestException;
import com.nevitoniuri.essentials.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .title("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .userMessage(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}