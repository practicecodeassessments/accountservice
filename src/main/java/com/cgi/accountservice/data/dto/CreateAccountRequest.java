package com.cgi.accountservice.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequest {

    private long customerId;
    private double initialCredit;
}
