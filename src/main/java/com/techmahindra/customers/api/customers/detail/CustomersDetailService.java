package com.techmahindra.customers.api.customers.detail;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.repository.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
import com.techmahindra.customers.constants.ApplicationMappers;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomersDetailService {

    private final CustomerRepository repository;

    public CustomerDto detailById(String id) throws NotFoundException {

        Long customerId;

        try {
            customerId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id=" + id + " is invalid");
        }

        Optional<Customer> optionalCustomer = repository.findById(customerId);

        CustomerDto customerDto = optionalCustomer
                .map(customer -> ApplicationMappers.CUSTOMER_MAPPER.toCustomerDto(customer))
                .orElseThrow(() -> new NotFoundException("Customer not found for id=" + id));

        return customerDto;
    }
}
