package com.example.ecommerce.exception;

import com.example.ecommerce.entity.Card;

public class CardAlreadyExistsException extends Exception{
    public CardAlreadyExistsException(String message){
        super(message);
    }
}
