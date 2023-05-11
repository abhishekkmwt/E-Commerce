package com.example.ecommerce.service;

import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.IndividualProductCheckoutRequest;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;

import java.util.List;


public interface OrderedService {

    public OrderedResponse placeOrder(IndividualProductCheckoutRequest individualProductCheckoutRequest) throws Exception;

    public List<OrderedResponse> getAllOrders(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest);
}
