package com.example.ecommerce.exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String message){
        super(message);
    }
}
