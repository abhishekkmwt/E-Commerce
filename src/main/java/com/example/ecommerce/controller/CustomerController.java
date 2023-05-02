package com.example.ecommerce.controller;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dtos.requestDto.CustomerByEmailOrMobileRequest;
import com.example.ecommerce.dtos.requestDto.CustomerUpdateRequest;
import com.example.ecommerce.dtos.requestDto.CustomerRequest;
import com.example.ecommerce.dtos.responseDto.CustomerResponse;
import com.example.ecommerce.dtos.responseDto.CustomerUpdateResponse;
import com.example.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        try{
            CustomerResponse customerResponse =customerService.addCustomer(customerRequest);
            return new ResponseEntity(customerResponse, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-customer")
    public ResponseEntity getAllCustomer(){
        return new ResponseEntity(customerService.getAllCustomer(),HttpStatus.CREATED);
    }

    @GetMapping("/get-customer")
    public ResponseEntity getCustomerByEmailOrMobile(@RequestBody CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest){
        try{
            CustomerResponse customerResponse = customerService.getCustomerByEmailOrMobile(customerByEmailOrMobileRequest);
            return new ResponseEntity(customerResponse,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-customer-using-specific-card/{card}")
    public ResponseEntity getAllCustomerUsingSpecificCard(@PathVariable("card")CardType cardType){
            return new ResponseEntity(customerService.getAllCustomerUsingSpecificCard(cardType),HttpStatus.CREATED);
    }

    @PutMapping("/update-customer-based-on-email")
    public ResponseEntity updateCustomerByEmail(@RequestBody CustomerUpdateRequest customerUpdateRequest){
        try{
            CustomerUpdateResponse customerUpdateResponse =customerService.updateCustomerByEmail(customerUpdateRequest);
            return new ResponseEntity(customerUpdateResponse,HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-customer-by-email-or-mobile")
    public ResponseEntity deleteCustomerByEmailOrMobile(@RequestBody CustomerByEmailOrMobileRequest customerByEmailOrMobileRequest){
        try{
            customerService.deleteCustomerByEmailOrMobile(customerByEmailOrMobileRequest);
            return new ResponseEntity("Customer Deleted Successfully",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
