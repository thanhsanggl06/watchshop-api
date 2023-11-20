package com.api.watchshop.service;

import com.api.watchshop.dto.OrderRequest;
import com.api.watchshop.dto.OrderResponse;
import com.api.watchshop.dto.TrackingOrderResponse;
import com.api.watchshop.entity.Order;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);
    List<TrackingOrderResponse> getOrdersByPhoneNumber(String phoneNumber);
    List<TrackingOrderResponse> getOrdersByOrderStatus(String orderStatus);
    List<TrackingOrderResponse> getOrdersByOrderDateBetween(LocalDateTime starDate, LocalDateTime endDate);
    List<Object[]> getDailyRevenueBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    List<Object[]>getRevenueByCategoryBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    OrderResponse updateOrderStatus(long orderId, String newStatus);
}
