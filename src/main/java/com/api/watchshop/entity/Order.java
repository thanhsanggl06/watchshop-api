package com.api.watchshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "orders" )
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private double amount;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String shippingAddress;
    @CreationTimestamp
    private LocalDateTime orderDate;
    private String orderStatus;
    @OneToMany(mappedBy = "order" , fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;


}
