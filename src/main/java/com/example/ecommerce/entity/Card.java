package com.example.ecommerce.entity;

import com.example.ecommerce.Enums.CardType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="card")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true,nullable = false)
    String cardNo;

    Date expiryDate;

    Integer cvv;

    @Enumerated(EnumType.STRING)
    CardType cardType;

    @ManyToOne
    @JoinColumn
    Customer customer;

}