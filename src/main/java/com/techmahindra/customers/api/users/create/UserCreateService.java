package com.techmahindra.customers.api.users.create;

import com.techmahindra.customers.api.users.common.UserDto;
import com.techmahindra.customers.repository.entity.User;
import com.techmahindra.customers.repository.UserRepository;
import com.techmahindra.customers.constants.ApplicationMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCreateService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    public UserDto create(UserDto userRequest) {

        Optional<User> existentUser = repository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());

        if (existentUser.isPresent())
            throw new IllegalArgumentException("The username=" + userRequest.getUsername() + " and/or email=" + userRequest.getEmail() + " is already in use");

        User user = ApplicationMappers.USER_MAPPER.toUser(userRequest);

        user.setPassword(encoder.encode(user.getPassword()));

        User persistedUser = repository.save(user);

        UserDto response = ApplicationMappers.USER_MAPPER.toUserDto(persistedUser);

        return response;
    }
}
