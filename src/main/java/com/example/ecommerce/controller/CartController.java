package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestDto.CheckoutRequest;
import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.dtos.responseDto.CartResponse;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ItemService itemService;

    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(@RequestBody ItemRequest itemRequest){
        try{
            Item item = itemService.addItem(itemRequest);
            CartResponse cartResponse =cartService.addToCart(itemRequest.getCustomerId(), item);
            return new ResponseEntity(cartResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout-cart")
    public ResponseEntity checkOutCart(@RequestBody CheckoutRequest cartCheckoutRequest){
        try{
            OrderedResponse orderedResponse=cartService.checkOutCart(cartCheckoutRequest);
            return new ResponseEntity(orderedResponse,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
