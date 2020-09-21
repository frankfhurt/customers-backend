package com.techmahindra.customers.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleRunTimeException(AuthenticationException e) {
        return error(FORBIDDEN, e);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleRunTimeException(IllegalArgumentException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleRunTimeException(MethodArgumentNotValidException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleRunTimeException(NotFoundException e) {
        return error(NOT_FOUND, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
