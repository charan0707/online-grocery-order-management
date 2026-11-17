package com.charan.grocery.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data 
@Entity 
@Table (name="customer_order")
public class Order {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnore
    @ManyToOne 
    @JoinColumn(name="customer_id")
    private Customer customer;


    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    private LocalDate orderDate;
    private BigDecimal totalPrice;
}
