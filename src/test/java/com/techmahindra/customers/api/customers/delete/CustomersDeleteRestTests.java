package com.techmahindra.customers.api.customers.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmahindra.customers.CustomersApplication;
import com.techmahindra.customers.api.customers.common.RestTesting;
import com.techmahindra.customers.repository.entity.Customer;
import com.techmahindra.customers.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomersApplication.class)
@AutoConfigureMockMvc
@SqlGroup({
    @Sql(scripts = "/scripts/insert_testing_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/scripts/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/scripts/delete_testing_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CustomersDeleteRestTests extends RestTesting {

    public static final String AUTH_ERROR_MSG = "Full authentication is required to access this resource";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository repository;

    @Test
    public void givenNonAuthenticatedRequest_whenDelete_thenForbiddenError() throws Exception {
        mvc.perform(get("/customers/1"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(AUTH_ERROR_MSG));
    }

    @Test
    public void givenValidId_whenDelete_thenSuccess() throws Exception {
        String token = login(mvc);

        mvc.perform(delete("/customers/1")
                .header("auth-token", token))
                .andExpect(status().isOk());

        Optional<Customer> byId = repository.findById(1L);
        assertFalse(byId.isPresent());
    }

    @Test
    public void givenNonExistingId_whenDelete_thenNotFoundException() throws Exception {
        String token = login(mvc);

        mvc.perform(delete("/customers/54")
                .header("auth-token", token))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.containsString("Customer not found for id=54")));
    }

    @Test
    public void givenInvalidId_whenDelete_thenIllegalArgumentException() throws Exception {
        String token = login(mvc);

        mvc.perform(delete("/customers/abc")
                .header("auth-token", token))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("id=abc is invalid")));
    }
}
