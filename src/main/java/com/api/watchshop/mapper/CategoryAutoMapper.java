package com.api.watchshop.mapper;

import com.api.watchshop.dto.CategoryRequest;
import com.api.watchshop.dto.CategoryResponse;
import com.api.watchshop.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryAutoMapper {
    CategoryAutoMapper MAPPER = Mappers.getMapper(CategoryAutoMapper.class);
    CategoryResponse mapToCategoryResponse(Category category);
    Category mapToCategory (CategoryRequest categoryRequest);
}
