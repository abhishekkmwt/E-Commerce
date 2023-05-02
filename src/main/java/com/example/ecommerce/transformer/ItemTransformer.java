package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.entity.Item;

public class ItemTransformer {
    public static Item itemRequestToItem(ItemRequest itemRequest){
        return Item.builder()
                .requiredQuantity(itemRequest.getRequiredQuantity())
                .build();
    }
}
