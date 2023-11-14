package com.api.watchshop.mapper;

import com.api.watchshop.dto.TrackingOrderDetailsResponse;
import com.api.watchshop.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductAutoMapper.class)
public interface OrderDetailsAutoMapper {
    OrderDetailsAutoMapper MAPPER = Mappers.getMapper(OrderDetailsAutoMapper.class);

    TrackingOrderDetailsResponse mapToTrackingOrderDetailsResponse(OrderDetails orderDetails);
}
