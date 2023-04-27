package com.example.ecommerce.transformer;

import com.example.ecommerce.dtos.requestDto.SellerEmailRequest;
import com.example.ecommerce.dtos.requestDto.SellerRequest;
import com.example.ecommerce.dtos.requestDto.SellerUpdateRequest;
import com.example.ecommerce.dtos.responseDto.SellerResponse;
import com.example.ecommerce.dtos.responseDto.SellerUpdateResponse;
import com.example.ecommerce.entity.Seller;

public class SellerTransformer {

    public static Seller sellerRequestToSeller(SellerRequest sellerRequest){
        return Seller.builder()
                .name(sellerRequest.getName())
                .age(sellerRequest.getAge())
                .mobNo(sellerRequest.getMobNo())
                .emailId(sellerRequest.getEmailId())
                .build();
    }

    public static SellerResponse sellerToSellerResponse(Seller seller){
        return SellerResponse.builder()
                .name(seller.getName())
                .age(seller.getAge())
                .build();
    }

    public static Seller sellerEmailRequestToSeller(SellerEmailRequest sellerEmailRequest){
        return Seller.builder()
                .emailId(sellerEmailRequest.getEmailId())
                .build();
    }


    public static SellerUpdateResponse sellerToSellerUpdateResponse(Seller seller){
        return SellerUpdateResponse.builder()
                .name(seller.getName())
                .emailId(seller.getEmailId())
                .mobNo(seller.getMobNo())
                .age(seller.getAge())
                .build();
    }
}
