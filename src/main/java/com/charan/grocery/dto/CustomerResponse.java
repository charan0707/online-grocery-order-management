
package com.charan.grocery.dto;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
