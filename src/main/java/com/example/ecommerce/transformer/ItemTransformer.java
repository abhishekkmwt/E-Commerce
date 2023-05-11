package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.dtos.responseDto.ItemResponse;
import com.example.ecommerce.entity.Item;

public class ItemTransformer {
    public static Item itemRequestToItem(ItemRequest itemRequest){
        return Item.builder()
                .requiredQuantity(itemRequest.getRequiredQuantity())
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
