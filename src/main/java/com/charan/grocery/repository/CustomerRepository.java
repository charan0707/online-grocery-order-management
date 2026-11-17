package com.charan.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charan.grocery.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    
}
