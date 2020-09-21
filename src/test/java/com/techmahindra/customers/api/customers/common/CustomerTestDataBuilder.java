package com.techmahindra.customers.api.customers.common;

import com.techmahindra.customers.repository.Customer;
import com.techmahindra.customers.constants.ApplicationMappers;

public class CustomerTestDataBuilder {

    public static CustomerDto createValidCustomerDto(Long customerId, Long addressId) {
        return CustomerDto.builder()
                .id(customerId)
                .name("Customer name")
                .email("valid@email.com")
                .cpf("12345678912")
                .address(AddressDto.builder()
                        .id(addressId)
                        .address("address")
                        .city("city")
                        .state("state")
                        .country("country")
                        .build()
                ).build();
    }

    public static Customer createValidCustomer() {
        CustomerDto dto = createValidCustomerDto(1L, 1L);

        return ApplicationMappers.CUSTOMER_MAPPER.toCustomer(dto);
    }
}
