package com.techmahindra.customers.repository;

import com.techmahindra.customers.repository.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByEmail(@Param(value = "email") String email);

}
