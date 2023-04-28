package com.example.ecommerce.transformer;

import com.example.ecommerce.Enums.ProductStatus;
import com.example.ecommerce.dtos.requestDto.ProductRequest;
import com.example.ecommerce.dtos.responseDto.ProductResponse;
import com.example.ecommerce.entity.Product;

public class ProductTransformer {

    public static Product productRequestToProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getProductName())
                .price(productRequest.getPrice())
                .productCategory(productRequest.getProductCategory())
                .quantity(productRequest.getQuantity())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponse productToProductResponse(Product product){
        return ProductResponse.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .sellerName(product.getSeller().getName())
                .build();
    }
}
