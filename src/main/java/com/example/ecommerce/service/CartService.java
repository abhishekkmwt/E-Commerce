package com.example.ecommerce.service;


import com.example.ecommerce.dtos.requestDto.CheckoutRequest;
import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.dtos.responseDto.CartResponse;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import org.hibernate.dialect.SelectItemReferenceStrategy;

public interface CartService {

    public CartResponse addToCart(int customerId, Item item);

    public OrderedResponse checkOutCart(CheckoutRequest cartCheckoutRequest) throws Exception;
}
