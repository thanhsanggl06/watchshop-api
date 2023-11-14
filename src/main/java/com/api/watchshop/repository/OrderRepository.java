package com.api.watchshop.repository;

import com.api.watchshop.entity.Customer;
import com.api.watchshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
}
