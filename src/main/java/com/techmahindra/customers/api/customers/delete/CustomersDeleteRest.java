package com.techmahindra.customers.api.customers.delete;

import com.techmahindra.customers.api.customers.common.CustomersRest;
import com.techmahindra.customers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomersDeleteRest extends CustomersRest {

    private final CustomersDeleteService service;

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteById(@PathVariable String id) throws NotFoundException {

        service.deleteById(id);
        
        return ResponseEntity.ok().build();
    }
}
