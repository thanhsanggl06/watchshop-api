package com.api.watchshop.service;

import com.api.watchshop.dto.OrderDetailsRequest;
import com.api.watchshop.entity.Order;
import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.Product;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailsService {
    List<OrderDetails> create(List<OrderDetailsRequest> orderDetailsRequests, Order order);
    List<Object[]> getBestSellingProductsBetweenDates(LocalDateTime startDate,
                                               LocalDateTime endDate);
}
