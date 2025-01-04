package com.alten.mehdilagdimi.product.trial.business.exception;

public class ImageFailToSaveException extends RuntimeException{
    public ImageFailToSaveException(String msg){
        super(msg);
    }

    public ImageFailToSaveException(String msg, Throwable cause){
        super(msg, cause);
    }
}
