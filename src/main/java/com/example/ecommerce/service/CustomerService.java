package com.example.ecommerce.service;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.CustomerUpdateRequest;
import com.example.ecommerce.dtos.requestDto.CustomerRequest;
import com.example.ecommerce.dtos.responseDto.CustomerResponse;
import com.example.ecommerce.dtos.responseDto.CustomerUpdateResponse;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.exception.EmailIdAlreadyExistsException;
import com.example.ecommerce.exception.MobileNumberAlreadyExistsException;

import java.util.List;

public interface CustomerService {

    public CustomerResponse addCustomer(CustomerRequest customerRequest) throws EmailIdAlreadyExistsException, MobileNumberAlreadyExistsException;

    public List<CustomerResponse> getAllCustomer();

    public CustomerResponse getCustomerByEmailOrMobile(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest) throws Exception;

    public List<CustomerResponse> getAllCustomerUsingSpecificCard(CardType cardType);

    public CustomerUpdateResponse updateCustomerByEmail(CustomerUpdateRequest customerUpdateRequest) throws CustomerNotFoundException;

    public void deleteCustomerByEmailOrMobile(CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest) throws Exception;
}
