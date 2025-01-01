package com.alten.mehdilagdimi.product.trial.business.exception;

public class UserNotFoundForAuthException extends RuntimeException{
    public UserNotFoundForAuthException(String identifier){
        super("User not found for authentication with provided email/id : " + identifier);
    }
}
