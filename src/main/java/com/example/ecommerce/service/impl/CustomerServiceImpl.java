package com.example.ecommerce.service.impl;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.CustomerUpdateRequest;
import com.example.ecommerce.dtos.requestDto.CustomerRequest;
import com.example.ecommerce.dtos.responseDto.CustomerResponse;
import com.example.ecommerce.dtos.responseDto.CustomerUpdateResponse;
import com.example.ecommerce.dtos.responseDto.SellerUpdateResponse;
import com.example.ecommerce.entity.Card;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.Seller;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.exception.EmailIdAlreadyExistsException;
import com.example.ecommerce.exception.MobileNumberAlreadyExistsException;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) throws EmailIdAlreadyExistsException, MobileNumberAlreadyExistsException {
        if(customerRepository.findByEmailId(customerRequest.getEmailId()) != null){
            throw new EmailIdAlreadyExistsException("EmailId Already Exists!!");
        }
        if(customerRepository.findByMobNo(customerRequest.getMobNo()) !=null){
            throw new MobileNumberAlreadyExistsException("Mobile Number Already Exists!!");
        }
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Cart cart= Cart.builder()
                .totalCost(0)
                .noOfItems(0)
                .customer(customer)
                .build();
        customer.setCart(cart);
        customerRepository.save(customer);
        CustomerResponse customerResponse=CustomerTransformer.customerToCustomerResponse(customer);

        return customerResponse;
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerList =customerRepository.findAll();
        List<CustomerResponse> customerResponseList =new ArrayList<>();
        for(Customer customer : customerList){
            CustomerResponse customerResponse = CustomerTransformer.customerToCustomerResponse(customer);
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Override
    public CustomerResponse getCustomerByEmailOrMobile(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest) throws Exception {
        if(customerByEmailOrMobileRequest.getEmailId() != null){
           Customer customer= customerRepository.findByEmailId(customerByEmailOrMobileRequest.getEmailId());
            if(customer == null){
                throw new CustomerNotFoundException("Customer Not Exists!!");
            }
            CustomerResponse customerResponse =CustomerTransformer.customerToCustomerResponse(customer);
            return customerResponse;
        }
        else if (customerByEmailOrMobileRequest.getMobNo() != null){
            Customer customer= customerRepository.findByMobNo(customerByEmailOrMobileRequest.getMobNo());
            if(customer == null){
                throw new CustomerNotFoundException("Customer Not Exists!!");
            }
            CustomerResponse customerResponse =CustomerTransformer.customerToCustomerResponse(customer);
            return customerResponse;
        }
        else{
            throw new Exception("Both Email Or Mobile Number Cannot be null");
        }
    }

    @Override
    public List<CustomerResponse> getAllCustomerUsingSpecificCard(CardType cardType) {
        List<Card> cardList = cardRepository.findByCardType(cardType);
        List<CustomerResponse> customerResponseList =new ArrayList<>();
        for(Card card : cardList){
           Customer customer =card.getCustomer();
           CustomerResponse customerResponse =CustomerTransformer.customerToCustomerResponse(customer);
           if(customerResponseList.contains(customerResponse)){
               continue;
           }
           customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Override
    public CustomerUpdateResponse updateCustomerByEmail(CustomerUpdateRequest customerUpdateRequest) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findByEmailId(customerUpdateRequest.getEmailId());
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Customer Does Not Exists!!");
        }
        if(customerUpdateRequest.getAddress() == null){}
        else{
            customer.setAddress(customerUpdateRequest.getAddress());
        }
        if(customerUpdateRequest.getMobNo() == null){}
        else{
            customer.setMobNo(customerUpdateRequest.getMobNo());
        }
        if(customerUpdateRequest.getAge() == 0){}
        else{
            customer.setAge(customerUpdateRequest.getAge());
        }
        if(customerUpdateRequest.getName() == null){}
        else{
            customer.setMobNo(customerUpdateRequest.getName());
        }
        customer.setEmailId(customerUpdateRequest.getEmailId());
        customerRepository.save(customer);
        CustomerUpdateResponse customerUpdateResponse = CustomerTransformer.customerToCustomerUpdateResponse(customer);
        return customerUpdateResponse;
    }

    @Override
    public void deleteCustomerByEmailOrMobile(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest) throws Exception {
        if(customerByEmailOrMobileRequest.getEmailId() != null){
            Customer customer= customerRepository.findByEmailId(customerByEmailOrMobileRequest.getEmailId());
            if(customer == null){
                throw new CustomerNotFoundException("Customer Not Exists!!");
            }
            customerRepository.delete(customer);
        }
        else if (customerByEmailOrMobileRequest.getMobNo() != null){
            Customer customer= customerRepository.findByMobNo(customerByEmailOrMobileRequest.getMobNo());
            if(customer == null){
                throw new CustomerNotFoundException("Customer Not Exists!!");
            }
            customerRepository.delete(customer);
        }
        else{
            throw new Exception("Both Email and Mobile Number Cannot be null");
        }
    }


}
