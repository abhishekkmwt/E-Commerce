package com.example.ecommerce.dtos.responseDto;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.Enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductResponse {
    String productName;

    Integer price;

    ProductCategory productCategory;

    String sellerName;

    ProductStatus productStatus;

}
