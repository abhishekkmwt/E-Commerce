package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.CustomerRequest;
import com.example.ecommerce.dtos.responseDto.CustomerResponse;
import com.example.ecommerce.dtos.responseDto.CustomerUpdateResponse;
import com.example.ecommerce.entity.Customer;

public class CustomerTransformer {
    public static Customer customerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .age(customerRequest.getAge())
                .mobNo(customerRequest.getMobNo())
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .emailId(customerRequest.getEmailId())
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmailId())
                .build();
    }

    public static CustomerUpdateResponse customerToCustomerUpdateResponse(Customer customer){
        return CustomerUpdateResponse.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .age(customer.getAge())
                .mobNo(customer.getMobNo())
                .emailId(customer.getEmailId())
                .build();
    }
}
