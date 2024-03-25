package com.cgi.accountservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND )
@RequiredArgsConstructor
public class AccountNotFoundException extends CreateTransactionException {
    String field;
    String message;

    public AccountNotFoundException( String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}