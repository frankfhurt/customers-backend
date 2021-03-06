package com.techmahindra.customers.api.customers.delete;

import com.techmahindra.customers.repository.entity.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomersDeleteService {

    private final CustomerRepository repository;

    public boolean deleteById(String id) throws NotFoundException {

        Long customerId;

        try {
            customerId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id=" + id + " is invalid");
        }

        Optional<Customer> optionalCustomer = repository.findById(customerId);
        repository.delete(optionalCustomer.orElseThrow(() -> new NotFoundException("Customer not found for id=" + id)));

        return true;
    }

}
