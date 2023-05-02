package com.example.ecommerce.service.impl;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.entity.Card;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.exception.CardAlreadyExistsException;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.CardService;
import com.example.ecommerce.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CardResponse addCard(CardRequest cardRequest) throws CardAlreadyExistsException, CustomerNotFoundException {
        Customer customer =customerRepository.findByEmailId(cardRequest.getEmailId());
        if(customer==null){
            throw new CustomerNotFoundException("Sorry!! Customer Not Exists.");
        }
        if(cardRepository.findByCardNo(cardRequest.getCardNo())!=null){
            throw new CardAlreadyExistsException("Card Already Exists!!");
        }
        Card card = CardTransformer.cardRequestToCard(cardRequest);

        card.setCustomer(customer);
        customer.getCardList().add(card);
        customerRepository.save(customer);

        CardResponse cardResponse =CardTransformer.cardToCardResponse(card);
        return cardResponse;
    }

    @Override
    public List<CardResponse> getAllParticularCard(String cardType) {
        List<Card> cardList = cardRepository.getAllParticularCard(cardType);
        List<CardResponse> cardResponseList =new ArrayList<>();
        for(Card card : cardList){
            CardResponse cardResponse = CardTransformer.cardToCardResponse(card);
            cardResponseList.add(cardResponse);
        }
        return cardResponseList;
    }

    @Override
    public List<CardResponse> getAllNonExpiredCards() {
        List<Card> cardList = cardRepository.getAllNonExpiredCards();
        List<CardResponse> cardResponseList =new ArrayList<>();
        for(Card card : cardList){
            CardResponse cardResponse = CardTransformer.cardToCardResponse(card);
            cardResponseList.add(cardResponse);
        }
        return cardResponseList;
    }

    @Override
    public List<CardResponse> getAllExpiredCards() {
        List<Card> cardList = cardRepository.getAllExpiredCards();
        List<CardResponse> cardResponseList =new ArrayList<>();
        for(Card card : cardList){
            CardResponse cardResponse = CardTransformer.cardToCardResponse(card);
            cardResponseList.add(cardResponse);
        }
        return cardResponseList;
    }

    @Override
    public CardType getCardTypeOfMostOccurredCardType() {
        List<Card> cardList =cardRepository.findAll();
        HashMap<CardType,Integer> hm =new HashMap<>();
        for(Card card : cardList){
            hm.put(card.getCardType(), hm.getOrDefault(card.getCardType(),0)+1);
        }
        int count = 0;
        CardType cardType =CardType.VISA;
        for(Map.Entry<CardType,Integer> entry : hm.entrySet()){
            if(entry.getValue() > count){
                count=entry.getValue();
                cardType=entry.getKey();
            }
        }
        return cardType;
    }
}
