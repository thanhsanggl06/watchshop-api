package com.api.watchshop.dto;

import com.api.watchshop.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingOrderResponse {
    private long id;
    private Customer customer;
    private double amount;private String shippingAddress;
    private LocalDateTime orderDate;
    private String orderStatus;
    private List<TrackingOrderDetailsResponse> orderDetails;
}
