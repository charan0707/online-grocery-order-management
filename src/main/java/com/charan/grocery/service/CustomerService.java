
package com.charan.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.charan.grocery.dto.CustomerRequest;
import com.charan.grocery.dto.CustomerResponse;
import com.charan.grocery.entity.Customer;
import com.charan.grocery.exception.ResourceNotFoundException;
import com.charan.grocery.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Convert Entity to Response DTO
    private CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());

        return response;
    }

    // Create Customer
    public CustomerResponse createCustomer(CustomerRequest request) {

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());

        Customer savedCustomer = customerRepository.save(customer);

        return convertToResponse(savedCustomer);
    }

    // Get Customer by ID
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Customer not found with id: " + id));

        return convertToResponse(customer);
    }

    // Get All Customers
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .toList();
    }

    // Update Customer
    public CustomerResponse updateCustomer(
            Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Customer not found with id: " + id));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());

        Customer updatedCustomer = customerRepository.save(customer);

        return convertToResponse(updatedCustomer);
    }

    // Delete Customer
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Customer not found with id: " + id));

        customerRepository.delete(customer);
    }
}
