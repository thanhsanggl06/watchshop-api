package com.api.watchshop.repository;

import com.api.watchshop.entity.Customer;
import com.api.watchshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByOrderStatus(String orderStatus);
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT DATE(o.orderDate) AS orderDate, SUM(o.amount) AS totalAmount " +
            "FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.orderStatus ='accept' "+
            "GROUP BY DATE(o.orderDate) ORDER BY DATE(o.orderDate)")
    List<Object[]> getDailyRevenueBetweenDates(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p.category.name, SUM(od.quantity * p.price) " +
            "FROM OrderDetails od " +
            "JOIN od.product p " +
            "WHERE od.order.orderDate BETWEEN :startDate AND :endDate AND od.order.orderStatus ='accept'" +
            "GROUP BY p.category.name")
    List<Object[]> getRevenueByCategoryBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
