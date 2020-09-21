package com.techmahindra.customers.api.customers.update;

import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomersRest;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CustomersUpdateRest extends CustomersRest {

    @Autowired
    CustomersUpdateService business;

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public CustomerDto update(@Valid @RequestBody CustomerDto request) throws NotFoundException {
        return business.update(request);
    }

}
