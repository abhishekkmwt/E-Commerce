package com.example.ecommerce.service;

import com.example.ecommerce.dtos.requestDto.*;
import com.example.ecommerce.dtos.responseDto.SellerResponse;
import com.example.ecommerce.dtos.responseDto.SellerUpdateResponse;
import com.example.ecommerce.exception.EmailIdAlreadyExistsException;
import com.example.ecommerce.exception.MobileNumberAlreadyExistsException;
import com.example.ecommerce.exception.SellerNotFoundException;

import java.util.List;

public interface SellerService {
    public SellerResponse addSeller(SellerRequest sellerRequest) throws EmailIdAlreadyExistsException, MobileNumberAlreadyExistsException;

    public SellerResponse getSellerByEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException;

    public SellerResponse getSellerById(SellerIdRequest sellerIdRequest) throws SellerNotFoundException;

    public List<SellerResponse> getAllSellers();

    public void deleteSellerByEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException;

    public void deleteSellerById(SellerIdRequest sellerIdRequest) throws SellerNotFoundException;

    public List<SellerResponse> getAllSellerOfParticularAge(SellerAgeRequest sellerAgeRequest);

    public SellerUpdateResponse updateSellerBasedOnEmail(SellerUpdateRequest sellerUpdateRequest) throws SellerNotFoundException;
}
