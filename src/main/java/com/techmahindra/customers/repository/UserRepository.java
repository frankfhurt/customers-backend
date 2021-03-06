package com.techmahindra.customers.repository;

import com.techmahindra.customers.repository.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

	Optional<User> findByUsernameOrEmail(@Param(value = "username") String username, @Param(value = "email") String email);

}
