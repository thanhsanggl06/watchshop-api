package com.api.watchshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingOrderDetailsResponse {
    private ProductResponse product;
    private int quantity;
    private double price;
}
