package com.techmahindra.customers.api.customers.detail;


import com.techmahindra.customers.api.customers.common.CustomerDto;
import com.techmahindra.customers.api.customers.common.CustomersRest;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomersDetailRest extends CustomersRest {

    private final CustomersDetailService businessService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CustomerDto detailById(@PathVariable String id) throws NotFoundException {
        return businessService.detailById(id);
    }
}