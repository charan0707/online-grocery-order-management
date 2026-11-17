
package com.charan.grocery.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class GroceryItemResponse {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
}
