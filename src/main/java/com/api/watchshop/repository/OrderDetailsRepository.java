package com.api.watchshop.repository;

import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.OrderDetailsId;
import com.api.watchshop.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {
    List<OrderDetails> findByProduct(Product product);
    @Query("SELECT od.product, SUM(od.quantity) AS totalQuantity " +
            "FROM OrderDetails od " +
            "WHERE od.order.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY od.product " +
            "ORDER BY SUM(od.quantity) DESC")
    List<Object[]> getBestSellingProductsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
