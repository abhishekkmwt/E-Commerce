package com.example.ecommerce.service.impl;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.Enums.ProductStatus;
import com.example.ecommerce.dtos.requestDto.ProductRequest;
import com.example.ecommerce.dtos.requestDto.SellerEmailRequest;
import com.example.ecommerce.dtos.requestDto.SellerIdAndProductIdRequest;
import com.example.ecommerce.dtos.responseDto.ProductResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Seller;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.exception.SellerNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.SellerRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller = sellerRepository.findById(productRequest.getSellerId()).get();
        }
        catch (Exception e){
            throw new SellerNotFoundException("Seller Not Found!!");
        }

        for(Product product : seller.getProductList()){
            if(product.getName().equals(productRequest.getProductName())){
                int quantity=product.getQuantity();
                quantity=quantity+productRequest.getQuantity();
                product.setQuantity(quantity);
                sellerRepository.save(seller);
                ProductResponse productResponse = ProductTransformer.productToProductResponse(product);
                return productResponse;
            }
        }

        Product product= ProductTransformer.productRequestToProduct(productRequest);
        product.setSeller(seller);
        seller.getProductList().add(product);
        sellerRepository.save(seller);

        ProductResponse productResponse = ProductTransformer.productToProductResponse(product);
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProductsByCategory(ProductCategory productCategory) {
        List<Product> productList = productRepository.findByProductCategory(productCategory);
        List<ProductResponse> productResponseList = new ArrayList<>();
        for(Product product: productList){
            ProductResponse productResponse=ProductTransformer.productToProductResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getAllProductsBySellerEmail(SellerEmailRequest sellerEmailRequest) throws SellerNotFoundException {
        Seller seller;
        try{
            seller = sellerRepository.findByEmailId(sellerEmailRequest.getEmailId());
        }
        catch (Exception e){
            throw new SellerNotFoundException("Seller Not Found!!");
        }
        List<Product> productList =seller.getProductList();
        List<ProductResponse> productResponseList =new ArrayList<>();
        for(Product product : productList){
            ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    @Override
    public void deleteProductBySellerIdAndProductId(SellerIdAndProductIdRequest sellerIdAndProductIdRequest) throws SellerNotFoundException, ProductNotFoundException {
        Seller seller = sellerRepository.findById(sellerIdAndProductIdRequest.getSellerId()).get();
        Product product = productRepository.findById(sellerIdAndProductIdRequest.getProductId()).get();

        if(seller == null){
            throw new SellerNotFoundException("Seller Not Exists!!");
        }
        if(product==null){
            throw new ProductNotFoundException("Product Does Not Exists!!");
        }

        productRepository.deleteById(sellerIdAndProductIdRequest.getProductId());
        seller.getProductList().remove(product);
        sellerRepository.save(seller);
    }

    @Override
    public List<ProductResponse> getTopFiveCheapestProducts() {
        List<Product> productList =productRepository.findAll();
        Collections.sort(productList, (a,b) ->a.getPrice()-b.getPrice());
        List<ProductResponse> productResponseList =new ArrayList<>();
        if(productList.size() < 5){
            for(Product product : productList){
                ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
                productResponseList.add(productResponse);
            }
            return productResponseList;
        }
        for(int i=0;i<5;i++){
            ProductResponse productResponse =ProductTransformer.productToProductResponse(productList.get(i));
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getTopFiveCostliestProducts() {
        List<Product> productList =productRepository.findAll();
        Collections.sort(productList, (a,b) ->b.getPrice()-a.getPrice());
        List<ProductResponse> productResponseList =new ArrayList<>();
        if(productList.size() < 5){
            for(Product product : productList){
                ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
                productResponseList.add(productResponse);
            }
            return productResponseList;
        }
        for(int i=0;i<5;i++){
            ProductResponse productResponse =ProductTransformer.productToProductResponse(productList.get(i));
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getOutOfStockProducts() {
        List<Product> productList=productRepository.findAll();
        List<ProductResponse> productResponseList =new ArrayList<>();
        for(Product product:productList){
            if(product.getProductStatus()==ProductStatus.NOT_AVAILABLE){
                ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
                productResponseList.add(productResponse);
            }
        }
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getAllAvailableProducts() {
        List<Product> productList=productRepository.findAll();
        List<ProductResponse> productResponseList =new ArrayList<>();
        for(Product product:productList){
            if(product.getProductStatus()==ProductStatus.AVAILABLE){
                ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
                productResponseList.add(productResponse);
            }
        }
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getAllProductWithQuantityLessThanTen() {
        List<Product> productList=productRepository.findAll();
        List<ProductResponse> productResponseList =new ArrayList<>();
        for(Product product:productList){
            if(product.getQuantity() < 10){
                ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
                productResponseList.add(productResponse);
            }
        }
        return productResponseList;
    }

    @Override
    public ProductResponse getCheapestProductInParticularCategory(ProductCategory productCategory) {
        List<Product> productList = productRepository.findByProductCategory(productCategory);
        Collections.sort(productList, (a,b)-> a.getPrice()-b.getPrice());
        ProductResponse productResponse = ProductTransformer.productToProductResponse(productList.get(0));
        return productResponse;
    }

    @Override
    public ProductResponse getCostliestProductInParticularCategory(ProductCategory productCategory) {
        List<Product> productList = productRepository.findByProductCategory(productCategory);
        Collections.sort(productList, (a,b)-> b.getPrice()-a.getPrice());
        ProductResponse productResponse = ProductTransformer.productToProductResponse(productList.get(0));
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts(int price, String productCategory) {
        List<Product> productList = productRepository.getAllProducts(price, productCategory);
        List<ProductResponse> productResponseList =new ArrayList<>();
        for(Product product : productList){
            ProductResponse productResponse =ProductTransformer.productToProductResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }


}
