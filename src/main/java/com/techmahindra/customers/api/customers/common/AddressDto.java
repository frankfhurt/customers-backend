package com.techmahindra.customers.api.customers.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class AddressDto {

    private Long id;

    @JsonProperty(required = true)
    @NotBlank(message = "address should not be blank")
    private String address;

    @JsonProperty(required = true)
    @NotBlank(message = "city should not be blank")
    private String city;

    @JsonProperty(required = true)
    @NotBlank(message = "state should not be blank")
    private String state;

    @JsonProperty(required = true)
    @NotBlank(message = "country should not be blank")
    private String country;

}