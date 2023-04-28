package com.example.ecommerce.dtos.requestDto;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.Enums.ProductStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String productName;

    Integer price;

    Integer quantity;

    ProductCategory productCategory;

    int sellerId;
}
