package com.example.ecommerce.dtos.requestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerUpdateRequest {
    String name;

    String emailId;

    String mobNo;

    int age;
}
