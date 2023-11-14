package com.api.watchshop.entity;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class OrderDetailsId implements Serializable {
    private Order order;
    private Product product;

}
