package com.api.watchshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty
    private String title;
    @Min(value = 0)
    private BigDecimal price;
    @Min(value = 0, message = "quantity should not be less than 0")
    private int quantity;
    private String supplier;
    private String imageUrl;
    private String description;
    @NotNull
    private long categoryId;
}
