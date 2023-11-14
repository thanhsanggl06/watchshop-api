package com.api.watchshop.mapper;

import com.api.watchshop.dto.ProductRequest;
import com.api.watchshop.dto.ProductResponse;
import com.api.watchshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CategoryAutoMapper.class)
public interface ProductAutoMapper {
    ProductAutoMapper MAPPER = Mappers.getMapper(ProductAutoMapper.class);

    Product mapToProduct(ProductRequest productRequest);
    ProductResponse mapToProductResponse(Product product);

}
