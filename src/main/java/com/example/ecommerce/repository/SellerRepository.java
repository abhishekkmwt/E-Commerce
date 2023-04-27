package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findByEmailId(String email);
    Seller findByMobNo(String mobile);

    List<Seller> findByAge(int age);
}
