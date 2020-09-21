package com.techmahindra.customers.api.customers.create;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomerTestDataBuilder;
import com.techmahindra.customers.api.customers.repository.Customer;
import com.techmahindra.customers.api.customers.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomersCreateServiceTest {

    @Mock
    private CustomerRepository repository;

    private CustomersCreateService customersCreateService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        customersCreateService = new CustomersCreateService(repository);
        when(repository.save(any(Customer.class))).thenReturn(CustomerTestDataBuilder.createValidCustomer());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
    }

    @Test
    public void givenValidCustomer_whenCreate_thenSuccess() {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(null, null);

        CustomerDto customerDto = customersCreateService.create(validCustomerDto);

        verify(repository).save(any(Customer.class));
        assertNotNull(customerDto);
        assertEquals(Long.valueOf(1), customerDto.getId());
        assertEquals(Long.valueOf(1), customerDto.getAddress().getId());
    }

    @Test
    public void givenValidCustomerWithExistingUserName_whenCreate_thenIllegalArgumentException() {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(null, null);
        Customer existingCustomer = CustomerTestDataBuilder.createValidCustomer();

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(existingCustomer));

        try {
            customersCreateService.create(validCustomerDto);
            fail("This line must not be reached");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("The email=valid@email.com is already in use", e.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void givenValidCustomer_whenCreate_thenExceptionWhenSave() {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(null, null);

        when(repository.save(any(Customer.class))).thenThrow(RuntimeException.class);

        customersCreateService.create(validCustomerDto);
    }
}
