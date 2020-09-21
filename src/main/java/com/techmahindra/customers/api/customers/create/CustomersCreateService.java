package com.techmahindra.customers.api.customers.create;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.repository.Customer;
import com.techmahindra.customers.api.customers.repository.CustomerRepository;
import com.techmahindra.customers.constants.ApplicationMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomersCreateService {

    private final CustomerRepository repository;

    public CustomerDto create(CustomerDto customerRequest) {

        Optional<Customer> existingCustomer = repository.findByEmail(customerRequest.getEmail());

        if (existingCustomer.isPresent())
            throw new IllegalArgumentException("The email=" + customerRequest.getEmail() + " is already in use");

        Customer customer = ApplicationMappers.CUSTOMER_MAPPER.toCustomer(customerRequest);

        Customer persistedUser = repository.save(customer);

        CustomerDto response = ApplicationMappers.CUSTOMER_MAPPER.toCustomerDto(persistedUser);

        return response;
    }
}
