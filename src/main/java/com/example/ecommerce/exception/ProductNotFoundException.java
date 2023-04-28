package com.example.ecommerce.exception;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message){
        super(message);
    }
}
