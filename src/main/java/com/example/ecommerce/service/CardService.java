package com.example.ecommerce.service;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.exception.CardAlreadyExistsException;
import com.example.ecommerce.exception.CustomerNotFoundException;

import java.util.Date;
import java.util.List;

public interface CardService {

    public CardResponse addCard(CardRequest cardRequest) throws CardAlreadyExistsException, CustomerNotFoundException;

    public List<CardResponse> getAllParticularCard(String cardType);

    public List<CardResponse> getAllNonExpiredCards();

    public List<CardResponse> getAllExpiredCards();

    public CardType getCardTypeOfMostOccurredCardType();
}
