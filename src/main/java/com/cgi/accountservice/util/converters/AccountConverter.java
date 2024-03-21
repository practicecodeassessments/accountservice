package com.cgi.accountservice.util.converters;

import com.cgi.accountservice.data.dto.AccountDto;
import com.cgi.accountservice.data.entity.Account;
import org.springframework.beans.BeanUtils;

public class AccountConverter extends BaseConverter<Account, AccountDto>{

    @Override
    public Account convertToEntity(AccountDto dto, Object... args) {
        Account entity = new Account();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "customer");
        }
        return entity;
    }

    @Override
    public AccountDto convertToDto(Account entity, Object... args) {
        AccountDto dto = new AccountDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "customer");
        }
        return dto;
    }
}
