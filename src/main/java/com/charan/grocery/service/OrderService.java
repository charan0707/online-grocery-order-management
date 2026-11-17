
package com.charan.grocery.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charan.grocery.dto.*;
import com.charan.grocery.entity.*;
import com.charan.grocery.exception.ResourceNotFoundException;
import com.charan.grocery.repository.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final GroceryItemRepository groceryItemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            GroceryItemRepository groceryItemRepository,
            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.groceryItemRepository = groceryItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setOrderDate(order.getOrderDate());
        response.setTotalPrice(order.getTotalPrice());

        List<OrderItemResponse> items = new ArrayList<>();

        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                OrderItemResponse itemResponse = new OrderItemResponse();
                itemResponse.setId(item.getId());
                itemResponse.setGroceryItemId(item.getGroceryItem().getId());
                itemResponse.setGroceryItemName(item.getGroceryItem().getName());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPriceAtOrder(item.getPriceAtOrder());
                itemResponse.setSubtotal(
                    item.getPriceAtOrder().multiply(
                        BigDecimal.valueOf(item.getQuantity())));
                items.add(itemResponse);
            }
        }

        response.setItems(items);
        return response;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Customer not found with id: " + request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest requestedItem : request.getItems()) {
            GroceryItem groceryItem = groceryItemRepository
                .findById(requestedItem.getGroceryItemId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Grocery item not found with id: "
                    + requestedItem.getGroceryItemId()));

            int quantity = requestedItem.getQuantity();

            if (groceryItem.getQuantity() < quantity) {
                throw new IllegalArgumentException(
                    "Insufficient stock for item: " + groceryItem.getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setGroceryItem(groceryItem);
            item.setQuantity(quantity);
            item.setPriceAtOrder(groceryItem.getPrice());

            BigDecimal subtotal = groceryItem.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

            total = total.add(subtotal);
            groceryItem.setQuantity(groceryItem.getQuantity() - quantity);
            groceryItemRepository.save(groceryItem);

            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return convertToResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Order not found with id: " + id));
        return convertToResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
            .stream().map(this::convertToResponse).toList();
    }

    @Transactional
    public OrderResponse updateOrder(Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Order not found with id: " + id));

        if (updatedOrder.getCustomer() != null
                && updatedOrder.getCustomer().getId() != null) {
            Customer customer = customerRepository
                .findById(updatedOrder.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Customer not found with id: "
                    + updatedOrder.getCustomer().getId()));
            order.setCustomer(customer);
        }

        if (updatedOrder.getOrderDate() != null) {
            order.setOrderDate(updatedOrder.getOrderDate());
        }

        return convertToResponse(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Order not found with id: " + id));

        if (order.getOrderItems() != null) {
            orderItemRepository.deleteAll(order.getOrderItems());
        }

        orderRepository.delete(order);
    }
}
