package com.cgi.accountservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CreateTransactionException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    protected CreateTransactionException() {
        super();
    }

    public CreateTransactionException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CreateTransactionException(HttpStatus httpStatus, String message, Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CreateTransactionException(HttpStatus httpStatus, String message, Throwable t) {
        super(message, t);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static CreateTransactionException of(HttpStatus code, String message) {
        return new CreateTransactionException(code, message);
    }
    public CreateTransactionException(String message) {
        super(message);
    }
}
