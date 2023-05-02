package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.responseDto.CartResponse;
import com.example.ecommerce.dtos.responseDto.ItemResponse;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Item;

public class CartTransformer {
    public static CartResponse cartToCartResponse(Cart cart){
        return CartResponse.builder()
                .customerName(cart.getCustomer().getName())
                .noOfItems(cart.getNoOfItems())
                .totalCost(cart.getTotalCost())
                .build();
    }

    public static ItemResponse itemToItemResponse(Item item){
        return ItemResponse.builder()
                .productName(item.getProduct().getName())
                .quantity(item.getRequiredQuantity())
                .totalItemsCost(item.getProduct().getPrice()*item.getRequiredQuantity())
                .singleItemCost(item.getProduct().getPrice())
                .build();
    }
}
