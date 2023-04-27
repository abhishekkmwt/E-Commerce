package com.example.ecommerce.exception;

public class EmailIdAlreadyExistsException extends Exception{
    public EmailIdAlreadyExistsException(String message){
        super(message);
    }
}
