package com.example.ecommerce.dtos.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SellerUpdateResponse {
    String name;

    String emailId;

    String mobNo;

    int age;
}
