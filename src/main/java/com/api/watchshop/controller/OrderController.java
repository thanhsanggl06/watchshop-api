package com.api.watchshop.controller;

import com.api.watchshop.dto.OrderRequest;
import com.api.watchshop.dto.OrderResponse;
import com.api.watchshop.dto.TrackingOrderResponse;
import com.api.watchshop.entity.Order;
import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.Product;
import com.api.watchshop.service.OrderDetailsService;
import com.api.watchshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private OrderDetailsService orderDetailsService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request){
        Order o = orderService.create(request);
        orderDetailsService.create(request.getItems(),o);

        OrderResponse response = new OrderResponse(o.getId(), o.getOrderStatus());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/tracking-order/{phoneNumber}")
    public ResponseEntity<List<TrackingOrderResponse>> trackingOrder(@PathVariable String phoneNumber){
        List<TrackingOrderResponse> ls = orderService.getOrdersByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(ls);
    }



}
