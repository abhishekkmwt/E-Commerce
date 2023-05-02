package com.example.ecommerce.dtos.requestDto;

import com.example.ecommerce.Enums.CardType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardRequest {
    String cardNo;

    Date expiryDate;

    Integer cvv;

    CardType cardType;

    String emailId;
}
