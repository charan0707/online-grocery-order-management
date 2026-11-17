package com.charan.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charan.grocery.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    
}
