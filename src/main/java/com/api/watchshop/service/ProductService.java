package com.api.watchshop.service;

import com.api.watchshop.dto.ProductRequest;
import com.api.watchshop.dto.ProductResponse;
import com.api.watchshop.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(long id, ProductRequest productRequest);
    ProductResponse delete(long id);
    ProductResponse getById(long id);
    List<ProductResponse> getPageProducts(int page, int size, String sortBy, String sortOrder);
    List<ProductResponse> getPageProductsByCategory(int page, int size, String sortBy, String sortOrder, Category category);

}
