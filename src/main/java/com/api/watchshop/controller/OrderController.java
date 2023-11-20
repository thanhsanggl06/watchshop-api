package com.api.watchshop.controller;

import com.api.watchshop.dto.*;
import com.api.watchshop.entity.Order;
import com.api.watchshop.entity.Product;
import com.api.watchshop.mapper.ProductAutoMapper;
import com.api.watchshop.service.EmailService;
import com.api.watchshop.service.OrderDetailsService;
import com.api.watchshop.service.OrderService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/order")
@CrossOrigin(origins ={ "http://localhost:4200", "http://localhost:4201"})
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private OrderDetailsService orderDetailsService;
    private EmailService emailService;
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        Order o = orderService.create(request);
        orderDetailsService.create(request.getItems(),o);

        OrderResponse response = new OrderResponse(o.getId(), o.getOrderStatus());
        Thread sendEmailThread = new Thread(() -> {
            try {
                String s = emailService.sendHtmlEmail(request);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        messagingTemplate.convertAndSend("/topic/notifications",response);
        sendEmailThread.start();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/tracking-order/{phoneNumber}")
    public ResponseEntity<List<TrackingOrderResponse>> trackingOrder(@PathVariable String phoneNumber){
        List<TrackingOrderResponse> ls = orderService.getOrdersByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/recentOrder")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<TrackingOrderResponse>> getRecentOrder(){
        List<TrackingOrderResponse> ls = orderService.getOrdersByOrderStatus("new");
        return ResponseEntity.ok(ls);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable("id") long id, @RequestParam("newStatus") String newStatus){
        OrderResponse response = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dailyRevenue")
    public ResponseEntity<List<DailyRevenue>> getDailyRevenueBetweenDates(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        List<Object[]> rs = orderService.getDailyRevenueBetweenDates(startDate, endDate);

        List<DailyRevenue> responses = new ArrayList<>();
        for(Object[] data : rs){
            LocalDate orderDate = ((Date) data[0]).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date =  orderDate.format(formatter);
            Double totalAmount = (Double) data[1];
            DailyRevenue dailyRevenue = new DailyRevenue(date,totalAmount);
            responses.add(dailyRevenue);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/bestSellingProducts")
    public ResponseEntity<List<BestSeller>> getBestSellingProductsBetweenDates(){
        List<Object[]> rs = orderDetailsService.getBestSellingProductsBetweenDates(LocalDateTime.of(2023,11,5,0,0,0),
                LocalDateTime.of(2024,11,16,0,0,0));
        List<BestSeller> responses = new ArrayList<>();
        for(Object[] data : rs){
            Product product = (Product) data[0];
            ProductResponse productResponse = ProductAutoMapper.MAPPER.mapToProductResponse(product);
            Long totalQuantity = (Long) data[1];

            BestSeller bestSeller = new BestSeller(productResponse,totalQuantity);
            responses.add(bestSeller);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/revenueByCategory")
    public ResponseEntity<List<RevenueByCategory>> getRevenueByCategoryBetweenDates(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                                    @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        List<Object[]> rs = orderService.getRevenueByCategoryBetweenDates(startDate, endDate);
        List<RevenueByCategory> responses = new ArrayList<>();
        for(Object[] data : rs){
            String categoryName = (String) data[0];
            BigDecimal revenueBd = (BigDecimal) data[1];
            double revenue =  revenueBd.doubleValue();

            RevenueByCategory revenueByCategory = new RevenueByCategory(categoryName,revenue);
            responses.add(revenueByCategory);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<List<TrackingOrderResponse>> findByOrderDateBetween(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        List<TrackingOrderResponse> ls = orderService.getOrdersByOrderDateBetween(startDate,endDate);
        return ResponseEntity.ok(ls);
    }





}
