package com.techmahindra.customers.api.customers.delete;

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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomersDeleteServiceTests {

    @Mock
    private CustomerRepository repository;

    private CustomersDeleteService customersDeleteService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customersDeleteService = new CustomersDeleteService(repository);
        Customer validCustomer = CustomerTestDataBuilder.createValidCustomer();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(validCustomer));
    }

    @Test
    public void givenValidId_whenDelete_thenSuccess() throws NotFoundException {
        boolean wasDeleted = customersDeleteService.deleteById("1");

        assertTrue(wasDeleted);
        verify(repository).findById(anyLong());
        verify(repository).delete(any(Customer.class));
    }

    @Test
    public void givenValidId_whenDelete_thenCustomerNotFound() throws NotFoundException {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            customersDeleteService.deleteById("1");
            fail("This line must not be reached");
        } catch (NotFoundException e) {
            assertNotNull(e);
            assertEquals("Customer not found for id=1", e.getMessage());
            verify(repository).findById(anyLong());
        }
    }

    @Test
    public void givenInvalidId_whenDelete_thenIllegalArgumentException() throws NotFoundException {
        try {
            customersDeleteService.deleteById("abc");
            fail("This line must not be reached");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("id=abc is invalid", e.getMessage());
            verify(repository, times(0)).findById(anyLong());
        }
    }
}
