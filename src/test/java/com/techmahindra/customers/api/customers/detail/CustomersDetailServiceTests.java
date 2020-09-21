package com.techmahindra.customers.api.customers.detail;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomerTestDataBuilder;
import com.techmahindra.customers.repository.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
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
public class CustomersDetailServiceTests {

    @Mock
    private CustomerRepository repository;

    private CustomersDetailService customersDetailService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customersDetailService = new CustomersDetailService(repository);
        Customer validCustomer = CustomerTestDataBuilder.createValidCustomer();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(validCustomer));
    }

    @Test
    public void givenValidId_whenDetailById_thenCustomerReturned() throws NotFoundException {
        CustomerDto customerDto = customersDetailService.detailById("1");

        verify(repository).findById(anyLong());
        assertNotNull(customerDto);
        assertEquals(Long.valueOf(1), customerDto.getId());
    }

    @Test
    public void givenValidId_whenDetailById_thenCustomerNotFound() throws NotFoundException {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            customersDetailService.detailById("1");
            fail("This line must not be reached");
        } catch (NotFoundException e) {
            assertNotNull(e);
            assertEquals("Customer not found for id=1", e.getMessage());
            verify(repository).findById(anyLong());
        }
    }

    @Test
    public void givenInvalidId_whenDetailById_thenIllegalArgumentException() throws NotFoundException {
        try {
            customersDetailService.detailById("abc");
            fail("This line must not be reached");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("id=abc is invalid", e.getMessage());
            verify(repository, times(0)).findById(anyLong());
        }
    }
}
