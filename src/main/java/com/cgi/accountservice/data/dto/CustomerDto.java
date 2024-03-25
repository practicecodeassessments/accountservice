package com.cgi.accountservice.data.dto;


import com.cgi.accountservice.data.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private long customerId;
    private List<Account> accounts;
}
