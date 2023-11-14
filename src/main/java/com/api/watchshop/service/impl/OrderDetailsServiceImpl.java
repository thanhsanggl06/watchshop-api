package com.api.watchshop.service.impl;

import com.api.watchshop.dto.OrderDetailsRequest;
import com.api.watchshop.entity.Order;
import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.Product;
import com.api.watchshop.exception.ResourceNotFoundException;
import com.api.watchshop.repository.OrderDetailsRepository;
import com.api.watchshop.repository.ProductRepository;
import com.api.watchshop.service.OrderDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private ProductRepository productRepository;
    private OrderDetailsRepository orderDetailsRepository;
    @Override
    public List<OrderDetails> create(List<OrderDetailsRequest> orderDetailsRequests, Order order) {

        List<OrderDetails> ls = new ArrayList<>();

        orderDetailsRequests.stream().forEach(od -> {
            Product p = productRepository.findById(od.getId()).orElseThrow(() -> new ResourceNotFoundException("Product", "ID", od.getId()));
            OrderDetails aod = OrderDetails.builder()
                    .order(order)
                    .product(p)
                    .price(od.getPrice())
                    .quantity(od.getQuantity())
                    .build();
            OrderDetails rs = orderDetailsRepository.save(aod);
            ls.add(rs);
        });
        return ls;
    }






}
