package com.techmahindra.customers.api.users.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    @JsonProperty(required = true)
    @NotBlank(message = "name should not be blank")
    private String name;

    @JsonProperty(required = true)
    @NotBlank(message = "username should not be blank")
    private String username;

    @JsonProperty(required = true)
    @NotBlank(message = "email should not be blank")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "bad email format")
    private String email;

    @JsonProperty(required = true)
    @NotBlank(message = "password should not be blank")
    private String password;

}