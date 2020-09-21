package com.techmahindra.customers.api.users.common;

import com.techmahindra.customers.api.users.repository.User;
import com.techmahindra.customers.constants.ApplicationMappers;

public class UserTestDataBuilder {

    public static UserDto createValidUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .name("Valid User Name")
                .username("validUserName")
                .email("valid@email.com")
                .password("password")
                .build();
    }

    public static User createValidUser() {
        UserDto dto = createValidUserDto(1L);

        return ApplicationMappers.USER_MAPPER.toUser(dto);
    }
}
