package com.techmahindra.customers.api.users.create;

import com.techmahindra.customers.api.users.common.UserDto;
import com.techmahindra.customers.api.users.common.UserTestDataBuilder;
import com.techmahindra.customers.repository.entity.User;
import com.techmahindra.customers.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomersCreateServiceTests {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    private UserCreateService userCreateService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userCreateService = new UserCreateService(repository, encoder);
        when(repository.save(any(User.class))).thenReturn(UserTestDataBuilder.createValidUser());
        when(repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(encoder.encode(any(CharSequence.class))).thenReturn("encondedPassword");
    }

    @Test
    public void givenValidUser_whenCreate_thenSuccess() {
        UserDto validUserDto = UserTestDataBuilder.createValidUserDto(null);

        UserDto customerDto = userCreateService.create(validUserDto);

        verify(repository).save(any(User.class));
        verify(encoder).encode("password");
        assertNotNull(customerDto);
        assertEquals(Long.valueOf(1), customerDto.getId());
    }

    @Test
    public void givenValidUserWithExistingUserName_whenCreate_thenIllegalArgumentException() {
        UserDto validUserDto = UserTestDataBuilder.createValidUserDto(null);
        User existingUser = UserTestDataBuilder.createValidUser();

        when(repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(existingUser));

        try {
            userCreateService.create(validUserDto);
            fail("This line must not be reached");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("The username=validUserName and/or email=valid@email.com is already in use", e.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void givenValidCustomer_whenCreate_thenExceptionWhenSave() {
        UserDto validCustomerDto = UserTestDataBuilder.createValidUserDto(null);

        when(repository.save(any(User.class))).thenThrow(RuntimeException.class);

        userCreateService.create(validCustomerDto);
    }
}
