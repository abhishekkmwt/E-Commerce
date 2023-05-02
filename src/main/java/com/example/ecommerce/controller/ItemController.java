package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;


    @PostMapping("/add")
    public ResponseEntity addItem(@RequestBody ItemRequest itemRequest){
     try{
         Item item =itemService.addItem(itemRequest);
         return new ResponseEntity(item,HttpStatus.CREATED);
     }catch (Exception e){
         return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
     }

    }
}
