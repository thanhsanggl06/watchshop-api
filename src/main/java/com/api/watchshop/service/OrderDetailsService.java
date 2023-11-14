package com.api.watchshop.service;

import com.api.watchshop.dto.OrderDetailsRequest;
import com.api.watchshop.entity.Order;
import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.Product;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetails> create(List<OrderDetailsRequest> orderDetailsRequests, Order order);
}
