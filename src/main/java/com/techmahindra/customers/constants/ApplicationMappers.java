package com.techmahindra.customers.constants;

import com.techmahindra.customers.api.customers.common.CustomerMapper;
import com.techmahindra.customers.api.users.common.UserMapper;
import org.mapstruct.factory.Mappers;

public class ApplicationMappers {

    public static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    public static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
}
