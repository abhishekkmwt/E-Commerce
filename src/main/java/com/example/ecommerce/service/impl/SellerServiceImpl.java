package com.example.ecommerce.service.impl;

import com.example.ecommerce.dtos.requestDto.*;
import com.example.ecommerce.dtos.responseDto.SellerResponse;
import com.example.ecommerce.dtos.responseDto.SellerUpdateResponse;
import com.example.ecommerce.entity.Seller;
import com.example.ecommerce.exception.EmailIdAlreadyExistsException;
import com.example.ecommerce.exception.MobileNumberAlreadyExistsException;
import com.example.ecommerce.exception.SellerNotFoundException;
import com.example.ecommerce.repository.SellerRepository;
import com.example.ecommerce.service.SellerService;
import com.example.ecommerce.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public SellerResponse addSeller(SellerRequest sellerRequest) throws EmailIdAlreadyExistsException, MobileNumberAlreadyExistsException {

     if(sellerRepository.findByEmailId(sellerRequest.getEmailId()) != null){
         throw new EmailIdAlreadyExistsException("EmailId Already Exists!!");
     }
     if(sellerRepository.findByMobNo(sellerRequest.getMobNo()) !=null){
         throw new MobileNumberAlreadyExistsException("Mobile Number Already Exists!!");
     }

     Seller seller = SellerTransformer.sellerRequestToSeller(sellerRequest);
     sellerRepository.save(seller);

     SellerResponse sellerResponse =SellerTransformer.sellerToSellerResponse(seller);

     return sellerResponse;
    }

    @Override
    public SellerResponse getSellerByEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException {
        Seller seller;
        try{
             seller=sellerRepository.findByEmailId(sellerEmailRequest.getEmailId());
        }catch (Exception e){
            throw new SellerNotFoundException("Seller Not Exists!!");
        }
        SellerResponse sellerResponse=SellerTransformer.sellerToSellerResponse(seller);
        return sellerResponse;
    }

    @Override
    public SellerResponse getSellerById(SellerIdRequest sellerIdRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller=sellerRepository.findById(sellerIdRequest.getId()).get();
        }catch (Exception e){
            throw new SellerNotFoundException("Seller Not Exists!!");
        }
        SellerResponse sellerResponse=SellerTransformer.sellerToSellerResponse(seller);
        return sellerResponse;
    }

    @Override
    public List<SellerResponse> getAllSellers() {
        List<Seller> sellerList = sellerRepository.findAll();
        List<SellerResponse> sellerResponseList = new ArrayList<>();
        for(Seller seller : sellerList){
            SellerResponse sellerResponse =SellerTransformer.sellerToSellerResponse(seller);
            sellerResponseList.add(sellerResponse);
        }
        return sellerResponseList;
    }

    @Override
    public void deleteSellerByEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller = sellerRepository.findByEmailId(sellerEmailRequest.getEmailId());
        }
        catch (Exception e){
            throw new SellerNotFoundException("Seller Does Not Exists!!");
        }
        sellerRepository.delete(seller);
    }

    @Override
    public void deleteSellerById(SellerIdRequest sellerIdRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller = sellerRepository.findById(sellerIdRequest.getId()).get();
        }
        catch (Exception e){
            throw new SellerNotFoundException("Seller Does Not Exists!!");
        }
        sellerRepository.delete(seller);
    }

    @Override
    public List<SellerResponse> getAllSellerOfParticularAge(SellerAgeRequest sellerAgeRequest) {
        List<Seller> sellerList = sellerRepository.findByAge(sellerAgeRequest.getAge());
        List<SellerResponse> sellerResponseList = new ArrayList<>();
        for(Seller seller : sellerList){
            SellerResponse sellerResponse=SellerTransformer.sellerToSellerResponse(seller);
            sellerResponseList.add(sellerResponse);
        }
        return sellerResponseList;
    }

    @Override
    public SellerUpdateResponse updateSellerBasedOnEmail(SellerUpdateRequest sellerUpdateRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller = sellerRepository.findByEmailId(sellerUpdateRequest.getEmailId());
        }
        catch(Exception e){
            throw new SellerNotFoundException("Seller Does Not Exists!!");
        }
        seller.setEmailId(sellerUpdateRequest.getEmailId());
        seller.setAge(sellerUpdateRequest.getAge());
        seller.setName(sellerUpdateRequest.getName());
        seller.setMobNo(sellerUpdateRequest.getMobNo());
        sellerRepository.save(seller);
        SellerUpdateResponse sellerUpdateResponse = SellerTransformer.sellerToSellerUpdateResponse(seller);
        return sellerUpdateResponse;
    }
}
