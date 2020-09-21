insert into address (id, create_date, last_update, version, address, city, country, state, customer_id)
            values (1, CURRENT_TIMESTAMP(), null, 1, 'address', 'city', 'country', 'state', null);
insert into customer (id, create_date, last_update, version, address_id, cpf, email, name)
            values (1, CURRENT_TIMESTAMP(), null, 1, 1, '12345678912', 'initial@email.com', 'name');