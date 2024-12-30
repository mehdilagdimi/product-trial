package com.alten.mehdilagdimi.product.trial.rest.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runTimeException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
