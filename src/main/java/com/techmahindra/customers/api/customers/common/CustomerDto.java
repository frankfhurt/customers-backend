package com.techmahindra.customers.api.customers.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class CustomerDto {

    private Long id;

    @JsonProperty(required = true)
    @NotBlank(message = "name should not be blank")
    private String name;

    @JsonProperty(required = true)
    @NotBlank(message = "email should not be blank")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "bad email format")
    private String email;

    @JsonProperty(required = true)
    @NotBlank(message = "cpf should not be blank")
    @Pattern(regexp = "^[0-9]{11}", message = "cpf must have 11 digits")
    private String cpf;

    @Valid
    @NotNull(message = "address should not be null")
    private AddressDto address;

}