package com.api.watchshop.service;

import com.api.watchshop.dto.CustomerRequest;
import com.api.watchshop.dto.CustomerResponse;
import com.api.watchshop.entity.Customer;

public interface CustomerService {
    Customer findByPhoneNumber(String phoneNumber);
    Customer create(CustomerRequest request);

}
