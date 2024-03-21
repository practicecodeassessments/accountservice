package com.cgi.accountservice.exception;


import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CreateTransactionException.class })
    protected ResponseEntity<Object> handleException(CreateTransactionException ex, WebRequest request) {

        Map<String, String> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("errorMessage", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { ParseException.class })
    protected ResponseEntity<Object> handleException(ParseException ex, WebRequest request) {

        Map<String, String> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("errorMessage", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
