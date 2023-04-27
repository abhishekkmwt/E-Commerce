package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestDto.*;
import com.example.ecommerce.dtos.responseDto.SellerResponse;
import com.example.ecommerce.dtos.responseDto.SellerUpdateResponse;
import com.example.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequest sellerRequest){
        try{
            SellerResponse sellerResponse =sellerService.addSeller(sellerRequest);
            return new ResponseEntity(sellerResponse, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-seller-by-email")
    public ResponseEntity getSellerByEmail(@RequestBody SellerEmailRequest sellerEmailRequest){
        try{
            SellerResponse sellerResponse =sellerService.getSellerByEmail(sellerEmailRequest);
            return new ResponseEntity(sellerResponse,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-seller-by-id")
    public ResponseEntity getSellerById(@RequestBody SellerIdRequest sellerIdRequest){
        try{
            SellerResponse sellerResponse =sellerService.getSellerById(sellerIdRequest);
            return new ResponseEntity(sellerResponse,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-sellers")
    public ResponseEntity getALlSellers(){
        try{
            List<SellerResponse> sellerResponseList =sellerService.getAllSellers();
            return new ResponseEntity(sellerResponseList,HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-seller-by-email")
    public ResponseEntity deleteSellerByEmail(@RequestBody SellerEmailRequest sellerEmailRequest){
        try{
            sellerService.deleteSellerByEmail(sellerEmailRequest);
            return new ResponseEntity("Seller Deleted Successfully.",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete-seller-by-id")
    public ResponseEntity deleteSellerById(@RequestBody SellerIdRequest sellerIdRequest){
        try{
            sellerService.deleteSellerById(sellerIdRequest);
            return new ResponseEntity("Seller Deleted Successfully.",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-seller-of-particular-age")
    public ResponseEntity getAllSellerOfParticularAge(@RequestBody SellerAgeRequest sellerAgeRequest){
        List<SellerResponse> sellerResponseList = sellerService.getAllSellerOfParticularAge(sellerAgeRequest);
        return new ResponseEntity(sellerResponseList,HttpStatus.CREATED);
    }

    @PutMapping("/update-seller-based-on-email")
    public ResponseEntity updateSellerBasedOnEmail(@RequestBody SellerUpdateRequest sellerUpdateRequest){
        try{
            SellerUpdateResponse sellerUpdateResponse =sellerService.updateSellerBasedOnEmail(sellerUpdateRequest);
            return new ResponseEntity(sellerUpdateResponse,HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
