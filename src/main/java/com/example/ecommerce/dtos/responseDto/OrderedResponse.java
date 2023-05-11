package com.example.ecommerce.dtos.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderedResponse {
    String orderNo;

    Date orderDate;

    Integer totalAmount;

    String cardUsed;

    List<ItemResponse> itemResponseList;

    String customerName;
}
