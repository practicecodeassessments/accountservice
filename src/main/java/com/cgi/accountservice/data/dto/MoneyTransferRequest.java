package com.cgi.accountservice.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MoneyTransferRequest
{
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private double moneyToBeTransferred;
}
