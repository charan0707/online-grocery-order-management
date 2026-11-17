package com.charan.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charan.grocery.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    
}
