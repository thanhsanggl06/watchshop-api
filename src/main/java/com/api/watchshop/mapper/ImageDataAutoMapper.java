package com.api.watchshop.mapper;

import com.api.watchshop.dto.ImageDataResponse;
import com.api.watchshop.entity.ImageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageDataAutoMapper {
    ImageDataAutoMapper MAPPER = Mappers.getMapper(ImageDataAutoMapper.class);

    ImageDataResponse mapToImageDataResponse(ImageData imageData);
}
