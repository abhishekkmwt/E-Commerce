package com.example.ecommerce.entity;

import com.example.ecommerce.Enums.CardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="card")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
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

    @CreationTimestamp
    Date date;

    @ManyToOne
    @JoinColumn
    Customer customer;

}
