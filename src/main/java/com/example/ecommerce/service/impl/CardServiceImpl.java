package com.example.ecommerce.service.impl;

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
}
