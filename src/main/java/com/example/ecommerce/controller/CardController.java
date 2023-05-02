package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestDto.CardRequest;
import com.example.ecommerce.dtos.responseDto.CardResponse;
import com.example.ecommerce.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
