package com.techmahindra.customers.api.users.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmahindra.customers.CustomersApplication;
import com.techmahindra.customers.api.users.common.UserDto;
import com.techmahindra.customers.api.users.common.UserTestDataBuilder;
import com.techmahindra.customers.api.users.repository.User;
import com.techmahindra.customers.api.users.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class UsersCreateRestTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository repository;

    @Test
    public void givenValidUser_whenCreate_thenSuccess() throws Exception {
        UserDto validUserDto = UserTestDataBuilder.createValidUserDto(null);
        String request = mapper.writeValueAsString(validUserDto);


        MvcResult mvcResult = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Optional<User> byUsernameOrEmail = repository.findByUsernameOrEmail(validUserDto.getName(), validUserDto.getEmail());
        assertTrue(byUsernameOrEmail.isPresent());

        User user = byUsernameOrEmail.get();
        String response = mvcResult.getResponse().getContentAsString();
        UserDto userDto = mapper.readValue(response, UserDto.class);

        assertEquals(user.getId(), userDto.getId());
    }

    @Test
    public void givenUserWithoutEmail_whenCreate_thenEmailError() throws Exception {
        UserDto validUserDto = UserTestDataBuilder.createValidUserDto(null);
        validUserDto.setEmail(null);
        String request = mapper.writeValueAsString(validUserDto);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("email should not be blank")));
    }
}
