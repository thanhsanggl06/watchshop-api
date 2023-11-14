package com.api.watchshop.service;

import com.api.watchshop.dto.OrderRequest;
import com.api.watchshop.dto.TrackingOrderResponse;
import com.api.watchshop.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);
    List<TrackingOrderResponse> getOrdersByPhoneNumber(String phoneNumber);
}
