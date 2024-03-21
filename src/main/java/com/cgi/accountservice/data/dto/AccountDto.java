package com.cgi.accountservice.data.dto;

import com.cgi.accountservice.data.entity.Customer;
import com.cgi.accountservice.util.enums.AccountStatus;
import com.cgi.accountservice.util.enums.AccountType;
import com.cgi.accountservice.util.enums.UpdateStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Customer customer;

    private String accountNumber;

    private AccountType type;

    private AccountStatus status;

    private double availableBalance;

    private double balance;

    private UpdateStatus lastBalanceUpdateStatus;

}
