package com.example.ecommerce.service.impl;

import com.example.ecommerce.Enums.ProductStatus;
import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.IndividualProductCheckoutRequest;
import com.example.ecommerce.dtos.responseDto.ItemResponse;
import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.OrderedRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderedService;
import com.example.ecommerce.transformer.ItemTransformer;
import com.example.ecommerce.transformer.OrderedTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderedServiceImpl implements OrderedService {
    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Ordered placeOrder (Customer customer, Card card) throws Exception {
        Ordered ordered =new Ordered();
        ordered.setOrderNo(String.valueOf(UUID.randomUUID()));
        String maskedNo = generateMaskedCard(card.getCardNo());
        ordered.setCardUsed(maskedNo);
        ordered.setCustomer(customer);
        List<Item> orderedItems =new ArrayList<>();
        for(Item item : customer.getCart().getItemList()){
            try{
                decreaseQuantity(item);
                orderedItems.add(item);
            }catch (Exception e){
                throw new Exception("Product Out Of Stock");
            }
        }
        for(Item item : orderedItems){
            item.setOrdered(ordered);
        }
        ordered.setItemList(orderedItems);
        ordered.setTotalAmount(customer.getCart().getTotalCost());
        return ordered;
    }

    public String generateMaskedCard(String cardNo){
        String maskedCardNo = "";
        for(int i=0;i< cardNo.length()-4;i++){
            maskedCardNo=maskedCardNo+'X';
        }
        maskedCardNo=maskedCardNo+cardNo.substring(cardNo.length()-4);
        return maskedCardNo;
    }

    public void decreaseQuantity(Item item) throws Exception {
        int currentQuantity=item.getProduct().getQuantity();
        int requiredQuantity=item.getRequiredQuantity();
        if(requiredQuantity > currentQuantity){
            throw new Exception("Product Out of Stock");
        }
        if(currentQuantity-requiredQuantity==0){
            item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        item.getProduct().setQuantity(currentQuantity-requiredQuantity);
    }

    @Override
    public OrderedResponse placeOrder(IndividualProductCheckoutRequest individualProductCheckoutRequest) throws Exception {
        Customer customer;
        try{
            customer = customerRepository.findById(individualProductCheckoutRequest.getCustomerId()).get();
        }catch (Exception e){
            throw new CustomerNotFoundException("Customer Does Not Exists");
        }

        Product product;
        try{
            product = productRepository.findById(individualProductCheckoutRequest.getProductId()).get();
        }catch (Exception e){
            throw new ProductNotFoundException("Product Does Not Exists");
        }

        Card card=cardRepository.findByCardNo(individualProductCheckoutRequest.getCardNumber());
        Date systemDate =new Date();
        int comparison = card.getExpiryDate().compareTo(systemDate);
        if(card == null || card.getCvv()!= individualProductCheckoutRequest.getCvv() || card.getCustomer()!= customer||comparison<0){
            throw new Exception("Invalid Card");
        }

        Item item = Item.builder()
                .requiredQuantity(individualProductCheckoutRequest.getRequiredQuantity())
                .product(product)
                .build();

        try{
            decreaseQuantity(item);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Ordered ordered =OrderedTransformer.individualProductCheckoutRequestToOrder(individualProductCheckoutRequest);
        ordered.setCardUsed(generateMaskedCard(card.getCardNo()));
        ordered.setCustomer(customer);
        ordered.setTotalAmount(product.getPrice()*individualProductCheckoutRequest.getRequiredQuantity());
        ordered.setItemList(new ArrayList<>());
        ordered.getItemList().add(item);

        customer.getOrderedList().add(ordered);
        item.setOrdered(ordered);
        product.getItemList().add(item);
        orderedRepository.save(ordered);

        OrderedResponse orderedResponse= OrderedTransformer.orderedToOrderedResponse(ordered);
        List<Item> itemList =ordered.getItemList();
        List<ItemResponse> itemResponseList =new ArrayList<>();
        for(Item item1 : itemList){
            ItemResponse itemResponse = ItemTransformer.itemToItemResponse(item1);
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
    }

    @Override
    public List<OrderedResponse> getAllOrders(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest) {
        Customer customer =null;
        if(customerByEmailOrMobileRequest.getMobNo() == null){
            customer = customerRepository.findByEmailId(customerByEmailOrMobileRequest.getEmailId());
        } else if (customerByEmailOrMobileRequest.getEmailId() == null) {
            customer = customerRepository.findByEmailId(customerByEmailOrMobileRequest.getMobNo());
        }

        List<Ordered> orderedList =customer.getOrderedList();
          List<OrderedResponse> orderedResponseList = new ArrayList<>();
          for(Ordered ordered: orderedList){
              OrderedResponse orderedResponse = OrderedTransformer.orderedToOrderedResponse(ordered);
              List<Item> itemList = ordered.getItemList();
              List<ItemResponse> itemResponseList =new ArrayList<>();
              for(Item item : itemList){
                  ItemResponse itemResponse = ItemTransformer.itemToItemResponse(item);
                  itemResponseList.add(itemResponse);
              }
              orderedResponse.setItemResponseList(itemResponseList);
              orderedResponse.setCustomerName(ordered.getCustomer().getName());
              orderedResponseList.add(orderedResponse);
          }
          return orderedResponseList;
    }
}
