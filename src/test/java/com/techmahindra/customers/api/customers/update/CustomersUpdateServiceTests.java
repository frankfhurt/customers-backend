package com.techmahindra.customers.api.customers.update;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomerTestDataBuilder;
import com.techmahindra.customers.api.customers.repository.Customer;
import com.techmahindra.customers.api.customers.repository.CustomerRepository;
import com.techmahindra.customers.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomersUpdateServiceTests {

    @Mock
    private CustomerRepository repository;

    private CustomersUpdateService customersUpdateService;

    private Customer existingCustomer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customersUpdateService = new CustomersUpdateService(repository);

        Customer updatedCustomer = CustomerTestDataBuilder.createValidCustomer();
        updatedCustomer.setName("Updated Name");
        when(repository.save(any(Customer.class))).thenReturn(updatedCustomer);

        existingCustomer = CustomerTestDataBuilder.createValidCustomer();
        when(repository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
    }

    @Test
    public void givenValidCustomer_whenUpdate_thenSuccess() throws NotFoundException {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(1L, 1L);

        assertEquals(existingCustomer.getName(), validCustomerDto.getName());

        CustomerDto customerDto = customersUpdateService.update(validCustomerDto);

        verify(repository).findById(anyLong());
        verify(repository).save(any(Customer.class));
        assertNotNull(customerDto);
        assertEquals(Long.valueOf(1), customerDto.getId());
        assertEquals(Long.valueOf(1), customerDto.getAddress().getId());
        assertEquals("Updated Name", customerDto.getName());
    }

    @Test(expected = NotFoundException.class)
    public void givenValidCustomer_whenUpdate_thenNotFound() throws NotFoundException {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(1L, 1L);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(existingCustomer.getName(), validCustomerDto.getName());

        CustomerDto customerDto = customersUpdateService.update(validCustomerDto);
    }
}
