package com.charan.grocery.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data 
@Entity 

public class OrderItem {

   @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @ManyToOne 
    @JoinColumn (name="order_id")
    private Order order;
    @ManyToOne 
    @JoinColumn (name="grocery_item_id")
    private GroceryItem groceryItem;

    private Integer quantity;
    private BigDecimal priceAtOrder;
}
