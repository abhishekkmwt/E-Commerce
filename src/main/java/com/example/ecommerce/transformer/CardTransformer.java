package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.entity.Card;

public class CardTransformer {
    public static Card cardRequestToCard(CardRequest cardRequest){
       return Card.builder()
               .cardNo(cardRequest.getCardNo())
               .cardType(cardRequest.getCardType())
               .cvv(cardRequest.getCvv())
               .expiryDate(cardRequest.getExpiryDate())
                .build();
    }

    public static CardResponse cardToCardResponse(Card card){
        return CardResponse.builder()
                .cardNo(card.getCardNo())
                .customerName(card.getCustomer().getName())
                .build();
    }
}
