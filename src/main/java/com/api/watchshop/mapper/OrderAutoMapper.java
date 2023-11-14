package com.api.watchshop.mapper;

import com.api.watchshop.dto.TrackingOrderResponse;
import com.api.watchshop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OrderDetailsAutoMapper.class)
public interface OrderAutoMapper {
    OrderAutoMapper MAPPER = Mappers.getMapper(OrderAutoMapper.class);

    TrackingOrderResponse mapToTrackingOrderResponse(Order order);
}
