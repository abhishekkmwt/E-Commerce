package com.example.ecommerce.service;

import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.entity.Item;

public interface ItemService {
    public Item addItem(ItemRequest itemRequest) throws Exception;
}
