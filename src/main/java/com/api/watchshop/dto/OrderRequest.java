package com.api.watchshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private CustomerRequest customerRequest;
    private String shippingAddress;
    private List<OrderDetailsRequest> items;

}
