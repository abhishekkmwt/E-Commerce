package com.example.ecommerce.service.impl;

import com.example.ecommerce.dtos.requestDto.CheckoutRequest;
import com.example.ecommerce.dtos.responseDto.CartResponse;
import com.example.ecommerce.dtos.responseDto.ItemResponse;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.transformer.CartTransformer;
import com.example.ecommerce.transformer.ItemTransformer;
import com.example.ecommerce.transformer.OrderedTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderedServiceImpl orderedServiceImpl;
    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    private JavaMailSender javaMailSender;

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
            ItemResponse itemResponse = ItemTransformer.itemToItemResponse(item1);

                itemResponseList.add(itemResponse);

        }
        cartResponse.setItemResponseList(itemResponseList);
        return cartResponse;
    }

    @Override
    public OrderedResponse checkOutCart(CheckoutRequest cartCheckoutRequest) throws Exception {
        Customer customer;
        try{
             customer = customerRepository.findById(cartCheckoutRequest.getCustomerId()).get();
        }catch (Exception e){
            throw new CustomerNotFoundException("Customer Does Not Exists");
        }

        Card card=cardRepository.findByCardNo(cartCheckoutRequest.getCardNumber());
        Date systemDate =new Date();
        int comparison = card.getExpiryDate().compareTo(systemDate);
        if(card == null || card.getCvv()!= cartCheckoutRequest.getCvv() || card.getCustomer()!= customer||comparison<0){
            throw new Exception("Invalid Card");
        }

        Cart cart = customer.getCart();
        if(cart.getTotalCost()==0){
            throw new Exception("Card is Empty!!");
        }
        Ordered ordered;
        try{
            ordered = orderedServiceImpl.placeOrder(customer,card);
            customer.getOrderedList().add(ordered);
            resetCart(cart);
            orderedRepository.save(ordered);


            OrderedResponse orderedResponse= OrderedTransformer.orderedToOrderedResponse(ordered);
            List<Item> itemList =ordered.getItemList();
            List<ItemResponse> itemResponseList =new ArrayList<>();
            for(Item item : itemList){
                ItemResponse itemResponse = ItemTransformer.itemToItemResponse(item);
                itemResponseList.add(itemResponse);
            }
            orderedResponse.setItemResponseList(itemResponseList);
            orderedResponse.setCustomerName(customer.getName());
            String text= "Hi "+customer.getName()+
                    " Your Order with Order ID: "+ordered.getOrderNo()+" has been successfully placed on "
                    +ordered.getOrderDate()+".";


            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom("dukaan.aapkiii@gmail.com");
            mailMessage.setTo(customer.getEmailId());
            mailMessage.setText(text);
            mailMessage.setSubject("Order Confirmed");

            // Sending the mail
            javaMailSender.send(mailMessage);
            return orderedResponse;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private void resetCart(Cart cart) {
        cart.setTotalCost(0);
        for(Item item : cart.getItemList()){
            item.setCart(null);
        }
        cart.setNoOfItems(0);
        cart.getItemList().clear();
    }
}
