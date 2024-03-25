package com.cgi.accountservice.exception;


import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CreateTransactionException.class })
    public ResponseEntity<ErrorResponse> handleCustomException( CreateTransactionException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = { ParseException.class })
    protected ResponseEntity<Object> handleCustomException(ParseException ex, WebRequest request) {

        Map<String, String> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("errorMessage", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    public static class ErrorResponse {
        private final String message;
        private final HttpStatus httpStatus;

        public ErrorResponse(String message, HttpStatus httpStatus) {
            this.message = message;
            this.httpStatus = httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> defaultErrorHandler(
            HttpServletRequest req, HttpServletResponse response, Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
