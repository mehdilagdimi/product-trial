package com.alten.mehdilagdimi.product.trial.business.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Product with ID : " + id + " is not found");
    }
}
