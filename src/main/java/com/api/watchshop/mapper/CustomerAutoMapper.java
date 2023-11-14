package com.api.watchshop.mapper;

import com.api.watchshop.dto.CustomerResponse;
import com.api.watchshop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerAutoMapper {
    CustomerAutoMapper MAPPER = Mappers.getMapper(CustomerAutoMapper.class);

    CustomerResponse mapToCustomerResponse(Customer customer);
}
