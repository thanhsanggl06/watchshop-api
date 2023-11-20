package com.api.watchshop.service.impl;

import com.api.watchshop.dto.OrderRequest;
import com.api.watchshop.dto.OrderResponse;
import com.api.watchshop.dto.TrackingOrderResponse;
import com.api.watchshop.entity.Customer;
import com.api.watchshop.entity.Order;
import com.api.watchshop.exception.ResourceNotFoundException;
import com.api.watchshop.mapper.OrderAutoMapper;
import com.api.watchshop.repository.OrderRepository;
import com.api.watchshop.service.CustomerService;
import com.api.watchshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private CustomerService customerService;
    private OrderRepository orderRepository;
    @Override
    public Order create(OrderRequest request) {
        Customer c = customerService.findByPhoneNumber(request.getCustomerRequest().getPhoneNumber());
        if(c == null){
           c = customerService.create(request.getCustomerRequest());
        }
        double amount = request.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        Order od = Order.builder().
                customer(c)
                        .orderStatus("new").amount(amount)
                                .shippingAddress(request.getShippingAddress()).build();

       return orderRepository.save(od);
    }

    @Override
    public List<TrackingOrderResponse> getOrdersByPhoneNumber(String phoneNumber) {
        Customer c = customerService.findByPhoneNumber(phoneNumber);
        if(c == null){
            throw new ResourceNotFoundException("customer","phone number",Long.parseLong(phoneNumber));
        }
        List<TrackingOrderResponse> ls = orderRepository.findByCustomer(c).stream().map(OrderAutoMapper.MAPPER::mapToTrackingOrderResponse).collect(Collectors.toList());
        return ls;
    }

    @Override
    public List<TrackingOrderResponse> getOrdersByOrderStatus(String orderStatus) {
        List<TrackingOrderResponse> ls = orderRepository.findByOrderStatus(orderStatus).stream().map(OrderAutoMapper.MAPPER::mapToTrackingOrderResponse).collect(Collectors.toList());
        return ls;
    }

    @Override
    public List<TrackingOrderResponse> getOrdersByOrderDateBetween(LocalDateTime starDate, LocalDateTime endDate) {
        List<TrackingOrderResponse> ls = orderRepository.findByOrderDateBetween(starDate, endDate).stream().map(OrderAutoMapper.MAPPER::mapToTrackingOrderResponse).collect(Collectors.toList());
        return ls;
    }

    @Override
    public List<Object[]> getDailyRevenueBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {

        return orderRepository.getDailyRevenueBetweenDates(startDate, endDate);
    }

    @Override
    public List<Object[]> getRevenueByCategoryBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getRevenueByCategoryBetweenDates(startDate, endDate);
    }

    @Override
    public OrderResponse updateOrderStatus(long orderId, String newStatus) {
        Order o =orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","id",orderId));
        o.setOrderStatus(newStatus);
        Order rs = orderRepository.save(o);
        OrderResponse response = new OrderResponse(rs.getId(),rs.getOrderStatus());
        return response;
    }
}
