package com.example.ecommerce.repository;

import com.example.ecommerce.dtos.responseDto.OrderedResponse;
import com.example.ecommerce.entity.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedRepository extends JpaRepository<Ordered,Integer> {


}
