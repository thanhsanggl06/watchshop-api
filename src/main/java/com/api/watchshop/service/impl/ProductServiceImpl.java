package com.api.watchshop.service.impl;

import com.api.watchshop.dto.ProductRequest;
import com.api.watchshop.dto.ProductResponse;
import com.api.watchshop.entity.Category;
import com.api.watchshop.entity.Product;
import com.api.watchshop.exception.ResourceNotFoundException;
import com.api.watchshop.mapper.ProductAutoMapper;
import com.api.watchshop.repository.CategoryRepository;
import com.api.watchshop.repository.ProductRepository;
import com.api.watchshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private CategoryRepository categoryRepository;
    private ProductRepository  productRepository;
    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = ProductAutoMapper.MAPPER.mapToProduct(productRequest);
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category","Id",productRequest.getCategoryId()));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        ProductResponse response = ProductAutoMapper.MAPPER.mapToProductResponse(savedProduct);
        return response;
    }

    @Override
    public ProductResponse getById(long id) {
        Product result = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product","Id",id));
        ProductResponse response = ProductAutoMapper.MAPPER.mapToProductResponse(result);
        return response;
    }

    @Override
    public List<ProductResponse> getPageProducts(int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        List<ProductResponse> ls =productRepository.findAll(pageable).stream().map(ProductAutoMapper.MAPPER::mapToProductResponse).collect(Collectors.toList());
        return ls;
    }

    @Override
    public List<ProductResponse> getPageProductsByCategory(int page, int size, String sortBy, String sortOrder, Category category) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        List<ProductResponse> ls =productRepository.findByCategory(category, pageable).stream().map(ProductAutoMapper.MAPPER::mapToProductResponse).collect(Collectors.toList());
        return ls;
    }

    @Override
    public ProductResponse update(long id, ProductRequest productRequest) {
        Product existProduct = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product","Id", id));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","Id", productRequest.getCategoryId()));
        existProduct.setCategory(category);
        existProduct.setDescription(productRequest.getDescription());
        existProduct.setPrice(productRequest.getPrice());
        existProduct.setQuantity(productRequest.getQuantity());
        existProduct.setTitle(productRequest.getTitle());
        existProduct.setSupplier(productRequest.getSupplier());
        existProduct.setImageUrl(productRequest.getImageUrl());
        Product updatedProduct = productRepository.save(existProduct);
        ProductResponse response = ProductAutoMapper.MAPPER.mapToProductResponse(updatedProduct);

        return response;
    }

    @Override
    public ProductResponse delete(long id) {
        Product existProduct = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product","Id", id));
        productRepository.delete(existProduct);
        ProductResponse response = ProductAutoMapper.MAPPER.mapToProductResponse(existProduct);
        return response;
    }


}
