package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.IndividualProductCheckoutRequest;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.service.OrderedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordered")
public class OrderedController {
    @Autowired
    OrderedService orderedService;

    @PostMapping("/place-order-for-individual-product")
    public ResponseEntity placeOrderForIndividualProduct(@RequestBody IndividualProductCheckoutRequest individualProductCheckoutRequest){
          try{
              OrderedResponse orderedResponse = orderedService.placeOrder(individualProductCheckoutRequest);
              return new ResponseEntity(orderedResponse, HttpStatus.CREATED);
          } catch (Exception e) {
              return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
          }
    }

    @GetMapping("/get-all-orders-from-customer")
    public ResponseEntity getAllOrders(@RequestBody CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest){
       return new ResponseEntity(orderedService.getAllOrders(customerByEmailOrMobileRequest),HttpStatus.CREATED);
    }
}
