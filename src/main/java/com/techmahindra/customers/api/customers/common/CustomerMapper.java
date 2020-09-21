package com.techmahindra.customers.api.customers.common;

import com.techmahindra.customers.repository.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer user);

}
