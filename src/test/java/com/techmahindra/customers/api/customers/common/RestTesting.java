package com.techmahindra.customers.api.customers.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class RestTesting {

    protected String login(MockMvc mvc) throws Exception {

        String request = "{\"username\": \"initialUsername\", \"password\": \"test123\"}";

        MvcResult mvcResult = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(header().exists("auth-token"))
                .andReturn();

        return mvcResult.getResponse().getHeader("auth-token");
    }
}
