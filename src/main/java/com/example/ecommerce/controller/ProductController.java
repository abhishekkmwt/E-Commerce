package com.example.ecommerce.controller;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.dtos.requestDto.ProductRequest;
import com.example.ecommerce.dtos.requestDto.SellerEmailRequest;
import com.example.ecommerce.dtos.requestDto.SellerIdAndProductIdRequest;
import com.example.ecommerce.dtos.responseDto.ProductResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequest productRequest){
        try{
            ProductResponse productResponse = productService.addProduct(productRequest);
            return new ResponseEntity(productResponse,HttpStatus.CREATED);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{category}")
    public ResponseEntity getAllProductsByCategory(@PathVariable("category")ProductCategory category){
        return new ResponseEntity(productService.getAllProductsByCategory(category),HttpStatus.CREATED);
    }

    @GetMapping("/get-all-product-by-seller-email")
    public ResponseEntity getAllProductsBySellerEmail(@RequestBody SellerEmailRequest sellerEmailRequest){
        try{
            List<ProductResponse> productResponseList=productService.getAllProductsBySellerEmail(sellerEmailRequest);
            return new ResponseEntity(productResponseList,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-product-by-sellerId-and-productId")
    public ResponseEntity deleteProductBySellerIdAndProductId(@RequestBody SellerIdAndProductIdRequest sellerIdAndProductIdRequest){
         try{
             productService.deleteProductBySellerIdAndProductId(sellerIdAndProductIdRequest);
             return new ResponseEntity("Product Deleted Successfully.",HttpStatus.CREATED);
         }catch (Exception e){
             return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
         }
    }

    @GetMapping("/top-five-cheapest-products")
    public ResponseEntity getTopFiveCheapestProducts(){
        return new ResponseEntity( productService.getTopFiveCheapestProducts(),HttpStatus.CREATED);
    }

    @GetMapping("/top-five-costliest-products")
    public ResponseEntity getTopFiveCostliestProducts(){
        return new ResponseEntity( productService.getTopFiveCostliestProducts(),HttpStatus.CREATED);
    }

    @GetMapping("/get-out-of-stock-products")
    public ResponseEntity getOutOfStockProducts(){
        return new ResponseEntity(productService.getOutOfStockProducts(),HttpStatus.CREATED);
    }

    @GetMapping("/get-all-available-products")
    public ResponseEntity getAllAvailableProducts(){
        return new ResponseEntity(productService.getAllAvailableProducts(),HttpStatus.CREATED);
    }

    @GetMapping("/get-all-product-with-quantity-less-than-ten")
    public ResponseEntity getAllProductWithQuantityLessThanTen(){
        return new ResponseEntity(productService.getAllProductWithQuantityLessThanTen(),HttpStatus.CREATED);
    }

    @GetMapping("get-cheapest-product-in particular-category/{category}")
    public ResponseEntity getCheapestProductInParticularCategory(@PathVariable("category") ProductCategory category){
        return new ResponseEntity(productService.getCheapestProductInParticularCategory(category),HttpStatus.CREATED);
    }

    @GetMapping("get-costliest-product-in particular-category/{category}")
    public ResponseEntity getCostliestProductInParticularCategory(@PathVariable("category") ProductCategory category){
        return new ResponseEntity(productService.getCostliestProductInParticularCategory(category),HttpStatus.CREATED);
    }

    @GetMapping("/get-All-Products/{price}/{category}")
    public ResponseEntity getAllProducts(@PathVariable("price") Integer price,@PathVariable("category") String category){
        return  new ResponseEntity(productService.getAllProducts(price,category),HttpStatus.CREATED);
    }

}
