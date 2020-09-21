package com.techmahindra.customers.api.customers.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmahindra.customers.CustomersApplication;
import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomerTestDataBuilder;
import com.techmahindra.customers.api.customers.common.RestTesting;
import com.techmahindra.customers.repository.entity.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomersApplication.class)
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(scripts = "/scripts/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/scripts/delete_testing_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CustomersUpdateRestTests extends RestTesting {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository repository;

    @Test
    public void givenValidCustomer_whenCreate_thenSuccess() throws Exception {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(null, null);
        String request = mapper.writeValueAsString(validCustomerDto);

        String token = login(mvc);

        MvcResult mvcResult = mvc.perform(post("/customers")
                .header("auth-token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Optional<Customer> byEmail = repository.findByEmail(validCustomerDto.getEmail());
        assertTrue(byEmail.isPresent());
        Customer customer = byEmail.get();

        String response = mvcResult.getResponse().getContentAsString();
        CustomerDto customerDto = mapper.readValue(response, CustomerDto.class);

        assertEquals(customer.getId(), customerDto.getId());
    }

    @Test
    public void givenCustomerWithoutEmail_whenCreate_thenEmailError() throws Exception {
        CustomerDto validCustomerDto = CustomerTestDataBuilder.createValidCustomerDto(null, null);
        validCustomerDto.setEmail(null);
        String request = mapper.writeValueAsString(validCustomerDto);

        String token = login(mvc);

        mvc.perform(post("/customers")
                .header("auth-token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("email should not be blank")));
    }
}
