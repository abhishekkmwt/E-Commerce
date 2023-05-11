package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.IndividualProductCheckoutRequest;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.Ordered;

import java.util.UUID;

public class OrderedTransformer {

    public static Ordered individualProductCheckoutRequestToOrder(IndividualProductCheckoutRequest individualProductCheckoutRequest){
        return Ordered.builder()
                .orderNo(String.valueOf(UUID.randomUUID()))
                .build();
    }
    public static OrderedResponse orderedToOrderedResponse(Ordered ordered){
        return OrderedResponse.builder()
                .orderNo(ordered.getOrderNo())
                .orderDate(ordered.getOrderDate())
                .totalAmount(ordered.getTotalAmount())
                .cardUsed(ordered.getCardUsed())
                .build();
    }
}
