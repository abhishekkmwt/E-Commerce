package com.example.ecommerce.service;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.dtos.requestDto.ProductRequest;
import com.example.ecommerce.dtos.requestDto.SellerEmailRequest;
import com.example.ecommerce.dtos.requestDto.SellerIdAndProductIdRequest;
import com.example.ecommerce.dtos.responseDto.ProductResponse;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponse addProduct(ProductRequest productRequest) throws SellerNotFoundException;

    public List<ProductResponse> getAllProductsByCategory(ProductCategory productCategory);

    public List<ProductResponse> getAllProductsBySellerEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException;

    public void deleteProductBySellerIdAndProductId(SellerIdAndProductIdRequest sellerIdAndProductIdRequest) throws SellerNotFoundException, ProductNotFoundException;

    public List<ProductResponse> getTopFiveCheapestProducts();

    public List<ProductResponse> getTopFiveCostliestProducts();

    public List<ProductResponse> getOutOfStockProducts();

    public List<ProductResponse> getAllAvailableProducts();

    public List<ProductResponse> getAllProductWithQuantityLessThanTen();

    public ProductResponse getCheapestProductInParticularCategory(ProductCategory productCategory);

    public ProductResponse getCostliestProductInParticularCategory(ProductCategory productCategory);

    public List<ProductResponse> getAllProducts(int price,String productCategory);
}
