package com.techmahindra.customers.api.customers.repository;

import com.techmahindra.customers.api.entity.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class Customer extends Model {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public void prepareAddressForUpdate(Address oldAddress) {
        if (address != null && oldAddress != null) {
            address.setId(oldAddress.getId());
            address.setVersion(oldAddress.getVersion());
        }
    }
}