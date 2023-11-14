package com.api.watchshop.dto;


import com.api.watchshop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private long id;
    private String title;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private CategoryResponse category;


}
