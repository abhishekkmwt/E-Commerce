package com.example.ecommerce.service;

import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.exception.CardAlreadyExistsException;
import com.example.ecommerce.exception.CustomerNotFoundException;

public interface CardService {

    public CardResponse addCard(CardRequest cardRequest) throws CardAlreadyExistsException, CustomerNotFoundException;
}
