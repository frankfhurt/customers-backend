package com.techmahindra.customers.configuration.security.userdetails;

import com.techmahindra.customers.api.users.repository.User;
import com.techmahindra.customers.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optUser = userRepository.findByUsernameOrEmail(username, username);

        User user = optUser.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("USER"));
    }
}