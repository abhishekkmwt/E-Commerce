package com.example.ecommerce.service.impl;

import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.dtos.responseDto.CartResponse;
import com.example.ecommerce.dtos.responseDto.ItemResponse;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.transformer.CardTransformer;
import com.example.ecommerce.transformer.CartTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public CartResponse addToCart(int customerId, Item item) {
        Customer customer =customerRepository.findById(customerId).get();
        Cart cart =customer.getCart();
        int newCost= cart.getTotalCost() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setTotalCost(newCost);
        cart.getItemList().add(item);
        int numberOfItems = cart.getNoOfItems() + 1;
        cart.setNoOfItems(numberOfItems);
        cartRepository.save(cart);

        CartResponse cartResponse = CartTransformer.cartToCartResponse(cart);
        List<Item> itemList =cart.getItemList();
        List<ItemResponse> itemResponseList =new ArrayList<>();
        for(Item item1 : itemList){
            ItemResponse itemResponse = CartTransformer.itemToItemResponse(item1);

                itemResponseList.add(itemResponse);

        }
        cartResponse.setItemResponseList(itemResponseList);
        return cartResponse;
    }
}
