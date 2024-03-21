package com.cgi.accountservice.controller;

import com.cgi.accountservice.data.dto.CreateAccountRequest;
import com.cgi.accountservice.data.dto.CreateCustomerRequest;
import com.cgi.accountservice.data.dto.MoneyTransferRequest;
import com.cgi.accountservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/***
 * This resource class serves the endpoint for Customer related functionality
 *
 * @author Rajesh Chanda
 * @version 0.1
 *
 */

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {
    private final CustomerService customerService;

    @PutMapping("/create-account")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequest request){
        log.info("create account request for customerId: {}",request.getCustomerId());
        return ResponseEntity.ok(customerService.createAccount(request));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity findCustomerByAccountNumber(@PathVariable(name = "accountNumber") String accountNumber) throws ParseException {
        log.info("getting transactions for the account number {}",accountNumber);
        return  ResponseEntity.ok(customerService.getTransactions(accountNumber));
    }

    @PostMapping("/create-customer")
    public ResponseEntity createCustomer(@RequestBody CreateCustomerRequest request){
        log.info("Creating the customer  {}",request.getFirstName());
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PostMapping("/money-transfer")
    public ResponseEntity moneyTransfer(@RequestBody MoneyTransferRequest request){
        log.info("Doing money transfer between   {} and  {}",request.getSourceAccountNumber() , request.getDestinationAccountNumber());
        return ResponseEntity.ok(customerService.moneyTransfer(request));
    }

}
