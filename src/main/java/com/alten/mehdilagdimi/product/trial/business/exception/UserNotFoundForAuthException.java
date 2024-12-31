package com.alten.mehdilagdimi.product.trial.business.exception;

public class UserNotFoundForAuthException extends RuntimeException{
    public UserNotFoundForAuthException(String email){
        super("User not found for authentication with provided email : " + email);
    }
}
