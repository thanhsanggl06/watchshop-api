package com.api.watchshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String url;
    @NotEmpty
    private String description;
    @NotEmpty
    private String imageUrl;
}
