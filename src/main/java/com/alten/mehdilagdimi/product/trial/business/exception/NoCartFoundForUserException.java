package com.alten.mehdilagdimi.product.trial.business.exception;

public class NoCartFoundForUserException extends RuntimeException{
    public NoCartFoundForUserException(String email){
        super("No cart was found for user with email : " + email);
    }
}
