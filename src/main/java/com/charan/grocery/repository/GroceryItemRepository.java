package com.charan.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charan.grocery.entity.GroceryItem;

public interface  GroceryItemRepository extends JpaRepository<GroceryItem,Long>{
    
}
