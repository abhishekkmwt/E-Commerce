package com.example.ecommerce.controller;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/add")
    public ResponseEntity addCard(@RequestBody CardRequest cardRequest){
       try{
           CardResponse cardResponse = cardService.addCard(cardRequest);
           return new ResponseEntity(cardResponse,HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/get-all-particular-card/{cardType}")
    public ResponseEntity getAllParticularCard(@PathVariable("cardType") String cardType){
        List<CardResponse> cardResponseList = cardService.getAllParticularCard(cardType);
        return new ResponseEntity(cardResponseList,HttpStatus.CREATED);
    }

    @GetMapping("/get-all-non-expired-cards")
    public ResponseEntity getAllNonExpiredCards(){
        List<CardResponse> cardResponseList = cardService.getAllNonExpiredCards();
        return new ResponseEntity(cardResponseList,HttpStatus.CREATED);
    }

    @GetMapping("/get-all-expired-cards")
    public ResponseEntity getAllExpiredCards(){
        List<CardResponse> cardResponseList = cardService.getAllExpiredCards();
        return new ResponseEntity(cardResponseList,HttpStatus.CREATED);
    }

    @GetMapping("/get-card-type")
    public ResponseEntity getCardTypeOfMostOccurredCardType(){
        CardType cardType = cardService.getCardTypeOfMostOccurredCardType();
        return new ResponseEntity(cardType,HttpStatus.CREATED);
    }
}
