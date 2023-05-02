package com.example.ecommerce.dtos.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerUpdateResponse {
    String name;

    String emailId;

    String mobNo;

    String address;

    int age;
}
