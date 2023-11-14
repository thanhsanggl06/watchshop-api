package com.api.watchshop.service.impl;

import com.api.watchshop.dto.OrderRequest;
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
}
