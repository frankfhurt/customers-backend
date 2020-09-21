package com.techmahindra.customers.api.customers.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmahindra.customers.CustomersApplication;
import com.techmahindra.customers.api.customers.common.RestTesting;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomersApplication.class)
@AutoConfigureMockMvc
@SqlGroup({
    @Sql(scripts = "/scripts/insert_testing_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/scripts/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/scripts/delete_testing_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CustomersDetailRestTest extends RestTesting {

    public static final String AUTH_ERROR_MSG = "Full authentication is required to access this resource";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenNonAuthenticatedRequest_whenDetail_thenForbiddenError() throws Exception {
        mvc.perform(get("/customers/1"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(AUTH_ERROR_MSG));
    }

    @Test
    public void givenValidId_whenDetail_thenCustomerReturned() throws Exception {
        String token = login(mvc);

        mvc.perform(get("/customers/1")
                .header("auth-token", token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("name")))
                .andExpect(jsonPath("$.email", Matchers.is("initial@email.com")))
                .andExpect(jsonPath("$.address", Matchers.notNullValue()));
    }

    @Test
    public void givenNonExistingId_whenDetail_thenNotFoundException() throws Exception {
        String token = login(mvc);

        mvc.perform(get("/customers/54")
                .header("auth-token", token))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.containsString("Customer not found for id=54")));
    }

    @Test
    public void givenInvalidId_whenDetail_thenIllegalArgumentException() throws Exception {
        String token = login(mvc);

        mvc.perform(get("/customers/abc")
                .header("auth-token", token))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("id=abc is invalid")));
    }
}
