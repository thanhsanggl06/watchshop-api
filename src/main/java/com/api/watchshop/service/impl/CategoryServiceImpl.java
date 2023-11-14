package com.api.watchshop.service.impl;

import com.api.watchshop.dto.CategoryRequest;
import com.api.watchshop.dto.CategoryResponse;
import com.api.watchshop.entity.Category;
import com.api.watchshop.exception.ResourceNotFoundException;
import com.api.watchshop.mapper.CategoryAutoMapper;
import com.api.watchshop.repository.CategoryRepository;
import com.api.watchshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public Category findByUrl(String url) {
        Category category = categoryRepository.findByUrl(url).orElseThrow(() -> new ResourceNotFoundException("Category", "URL", 0));
        return category;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = CategoryAutoMapper.MAPPER.mapToCategory(request);
        Category savedCategory = categoryRepository.save(category);
        CategoryResponse response = CategoryAutoMapper.MAPPER.mapToCategoryResponse(savedCategory);
        return response;
    }

    @Override
    public CategoryResponse getById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        CategoryResponse response = CategoryAutoMapper.MAPPER.mapToCategoryResponse(category);
        return response;
    }

    @Override
    public CategoryResponse update(long id, CategoryRequest request) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        existingCategory.setDescription(request.getDescription());
        existingCategory.setName(request.getName());
        existingCategory.setUrl(request.getUrl());
        existingCategory.setImageUrl(request.getImageUrl());

        Category updatedCategory = categoryRepository.save(existingCategory);
        CategoryResponse response = CategoryAutoMapper.MAPPER.mapToCategoryResponse(updatedCategory);
        return response;
    }

    @Override
    public CategoryResponse delete(long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(existingCategory);
        CategoryResponse response = CategoryAutoMapper.MAPPER.mapToCategoryResponse(existingCategory);
        return response;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> responses = categoryList.stream().map(CategoryAutoMapper.MAPPER::mapToCategoryResponse).collect(Collectors.toList());
        return responses;
    }
}
