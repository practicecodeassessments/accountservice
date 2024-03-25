package com.cgi.accountservice.util.converters;


import com.cgi.accountservice.model.AccountModel;
import com.cgi.accountservice.data.entity.Account;
import org.springframework.beans.BeanUtils;

public class AccountConverter extends BaseConverter<Account, AccountModel>{

    @Override
    public Account convertToEntity(AccountModel dto, Object... args) {
        Account entity = new Account();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "customer");
        }
        return entity;
    }

    @Override
    public AccountModel convertToDto( Account entity, Object... args) {
        AccountModel dto = new AccountModel();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "customer");
        }
        return dto;
    }
}
