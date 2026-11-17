
package com.charan.grocery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDate orderDate;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> items;
}
