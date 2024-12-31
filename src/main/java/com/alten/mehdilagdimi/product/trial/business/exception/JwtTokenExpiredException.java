package com.alten.mehdilagdimi.product.trial.business.exception;

public class JwtTokenExpiredException extends RuntimeException{
    public JwtTokenExpiredException(String subj){
        super("JWT token expired for subject : " + subj);
    }
}
