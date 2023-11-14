package com.api.watchshop.repository;

import com.api.watchshop.entity.OrderDetails;
import com.api.watchshop.entity.OrderDetailsId;
import com.api.watchshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {
    List<OrderDetails> findByProduct(Product product);
}
