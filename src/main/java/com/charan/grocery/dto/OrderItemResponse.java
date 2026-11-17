
package com.charan.grocery.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Long groceryItemId;
    private String groceryItemName;
    private Integer quantity;
    private BigDecimal priceAtOrder;
    private BigDecimal subtotal;
}
