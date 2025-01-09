package com.alten.mehdilagdimi.product.trial.rest.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runTimeException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse.getBody());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrityViolationException(DataIntegrityViolationException e){
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatusCode.valueOf(500), e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse.getBody());
    }

}
