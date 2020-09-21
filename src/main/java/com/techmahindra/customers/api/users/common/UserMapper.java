package com.techmahindra.customers.api.users.common;

import com.techmahindra.customers.repository.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

}
