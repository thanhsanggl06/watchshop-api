package com.api.watchshop.service;

import com.api.watchshop.dto.CategoryRequest;
import com.api.watchshop.dto.CategoryResponse;
import com.api.watchshop.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findByUrl(String url);
    CategoryResponse create(CategoryRequest request);
    CategoryResponse getById(long id);
    CategoryResponse update(long id, CategoryRequest request);
    CategoryResponse delete(long id);

    List<CategoryResponse> getAllCategories();

    interface CustomerService {
    }
}
