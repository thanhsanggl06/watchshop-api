package com.api.watchshop.service.impl;

import com.api.watchshop.dto.CustomerRequest;
import com.api.watchshop.dto.CustomerResponse;
import com.api.watchshop.entity.Customer;
import com.api.watchshop.mapper.CustomerAutoMapper;
import com.api.watchshop.repository.CustomerRepository;
import com.api.watchshop.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        Customer c = customerRepository.findByPhoneNumber(phoneNumber);
        return c;
    }

    @Override
    public Customer create(CustomerRequest request) {
        Customer c = Customer.builder().name(request.getName()).phoneNumber(request.getPhoneNumber()).build();
        Customer savedCustomer = customerRepository.save(c);
        return savedCustomer;
    }
}
