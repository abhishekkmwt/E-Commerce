package com.example.ecommerce.service.impl;

import com.example.ecommerce.Enums.ProductStatus;
import com.example.ecommerce.dtos.requestDto.ItemRequest;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ItemRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ItemService;
import com.example.ecommerce.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Item addItem(ItemRequest itemRequest) throws Exception {
        Customer customer = customerRepository.findById(itemRequest.getCustomerId()).get();
        Product product= productRepository.findById(itemRequest.getProductId()).get();
        if(customer==null){
            throw new CustomerNotFoundException("Customer Does Not Exists!!");
        }
        if(product==null){
            throw new ProductNotFoundException("Product Does Not Exists");
        }
        if(itemRequest.getRequiredQuantity() > product.getQuantity() || product.getProductStatus()!= ProductStatus.AVAILABLE){
            throw new Exception("Product Out of Stock.");
        }
        Item item = ItemTransformer.itemRequestToItem(itemRequest);
        item.setCart(customer.getCart());
        item.setProduct(product);
        product.getItemList().add(item);//In java if we update an existing object then we do not need to save the object explicitly.

        return itemRepository.save(item);
    }
}
