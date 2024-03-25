package com.cgi.accountservice.util.converters;

import com.cgi.accountservice.data.dto.CustomerDto;
import com.cgi.accountservice.data.entity.Customer;
import com.cgi.accountservice.model.AccountModel;
import org.springframework.beans.BeanUtils;

public class CustomerConverter extends BaseConverter<Customer, CustomerDto>{


    @Override
    public Customer convertToEntity(CustomerDto dto, Object... args) {
        Customer entity = new Customer();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "accounts");
        }
        return entity;
    }

    @Override
    public CustomerDto convertToDto( Customer entity, Object... args) {
        CustomerDto dto = new CustomerDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "accounts");
        }
        return dto;
    }
}
