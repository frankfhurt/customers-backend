package com.techmahindra.customers.api.customers.create;


import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomersRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CustomersCreateRest extends CustomersRest {

    private final CustomersCreateService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CustomerDto create(@Valid @RequestBody CustomerDto userRequest) {
        return service.create(userRequest);
    }
}