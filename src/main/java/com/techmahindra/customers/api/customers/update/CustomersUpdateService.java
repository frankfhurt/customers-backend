package com.techmahindra.customers.api.customers.update;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.repository.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
import com.techmahindra.customers.constants.ApplicationMappers;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomersUpdateService {

    private final CustomerRepository userRepository;

    public CustomerDto update(CustomerDto request) throws NotFoundException {

        Customer preUpdateUser = userRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException());

        Customer customer = ApplicationMappers.CUSTOMER_MAPPER.toCustomer(request);

        customer.prepareAddressForUpdate(preUpdateUser.getAddress());
        customer.setVersion(preUpdateUser.getVersion());

        Customer updatedCustomer = userRepository.save(customer);

        CustomerDto response = ApplicationMappers.CUSTOMER_MAPPER.toCustomerDto(updatedCustomer);

        return response;
    }
}