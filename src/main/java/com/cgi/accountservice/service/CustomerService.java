package com.cgi.accountservice.service;

import com.cgi.accountservice.data.dto.AccountDto;
import com.cgi.accountservice.data.dto.CreateAccountRequest;
import com.cgi.accountservice.data.dto.CreateCustomerRequest;
import com.cgi.accountservice.data.dto.CustomerDto;
import com.cgi.accountservice.data.dto.MoneyTransferRequest;
import com.cgi.accountservice.data.entity.Account;
import com.cgi.accountservice.data.entity.Customer;
import com.cgi.accountservice.exception.AccountNotFoundException;
import com.cgi.accountservice.exception.CreateTransactionException;
import com.cgi.accountservice.repository.AccountRepository;
import com.cgi.accountservice.repository.CustomerRepository;
import com.cgi.accountservice.rest.RestConnector;
import com.cgi.accountservice.util.AccountNoGenerator;
import com.cgi.accountservice.util.CustomerNoGenerator;
import com.cgi.accountservice.util.converters.AccountConverter;
import com.cgi.accountservice.util.converters.CustomerConverter;
import com.cgi.accountservice.util.enums.AccountStatus;
import com.cgi.accountservice.util.enums.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RestConnector restConnector;

    @Value("${transaction.service.url}")
    private String transactionServiceUrl;

    public CustomerDto getCustomerByCustomerId(long customerId){
        CustomerConverter converter = new CustomerConverter();
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);

        if(customerOptional.isPresent()){
            return converter.convertToDto(customerOptional.get());
        }

        return converter.convertToDto(new Customer());
    }

    @Transactional(rollbackFor = CreateTransactionException.class)
    public AccountDto createAccount(CreateAccountRequest req) throws CreateTransactionException {
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(req.getCustomerId());
        Customer customer = customerOptional.get();

        Account account = new Account();
        account.setAccountNumber(AccountNoGenerator.generate());
        account.setCustomer(customer);
        account.setType(AccountType.CURRENT);
        account.setAvailableBalance(req.getInitialCredit());
        account.setBalance(req.getInitialCredit());
        account.setStatus(AccountStatus.ACTIVE);
        Account createdAccount = accountRepository.save(account);

        if(req.getInitialCredit() != 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accountNo", createdAccount.getAccountNumber());
            jsonObject.put("amount", req.getInitialCredit());
            jsonObject.put("transactionType", "CREDIT");

            ResponseEntity<String> apiResponse = restConnector.sendPostRequest(transactionServiceUrl, jsonObject.toJSONString(), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);

            if(!apiResponse.getStatusCode().is2xxSuccessful()){
                throw new CreateTransactionException("Create transaction failed with response code: "+apiResponse.getStatusCode());
            }
        }


        List<Account> accounts = new ArrayList<>();
        accounts.add(createdAccount);
        customer.setAccounts(accounts);
        customerRepository.save(customer);

        return new AccountConverter().convertToDto(createdAccount);
    }

    public Long createCustomer( CreateCustomerRequest req) throws CreateTransactionException {
        Customer customer = new Customer();
        customer.setAddress( req.getAddress() );
        customer.setEmail( req.getEmail() );
        customer.setFirstName( req.getFirstName() );
        customer.setLastName( req.getLastName() );
        customer.setCustomerId( CustomerNoGenerator.generate() );
        customerRepository.save( customer );
        log.info("newly created customer number is " + customer.getCustomerId() );
        return customer.getCustomerId();
    }

    public String moneyTransfer( MoneyTransferRequest req) throws CreateTransactionException {
        if(req.getDestinationAccountNumber() == null || req.getSourceAccountNumber() == null ){
            log.info("account transfer can not be initiated ... "  );
            return "INVALID DETAILS FOR TRANSFER";
        }
/*        Optional<Account> srcAccountOptional = accountRepository.findByAccountNumber(req.getSourceAccountNumber());
        Account srcAccount = srcAccountOptional.get();*/
        Account srcAccount = accountRepository.findByAccountNumber(req.getSourceAccountNumber())
                .orElseThrow(()-> new AccountNotFoundException("sourceAccount", "Account not found"));

        Optional<Account> destAccountOptional = accountRepository.findByAccountNumber(req.getDestinationAccountNumber());
        Account destAccount = destAccountOptional.get();
        if(srcAccount.getAvailableBalance() < req.getMoneyToBeTransferred()){
            log.info("Money Can not be debited from source account");
            return "MONEY Transfer can not be iniatiated";
        }
        String moneyTransferUrl = transactionServiceUrl;
        if (srcAccount.getAvailableBalance() > req.getMoneyToBeTransferred()) {
            log.info("Money Can be debited from source account");
            JSONObject  jsonObjectCredit= new JSONObject();
            jsonObjectCredit.put("accountNo", req.getDestinationAccountNumber());
            jsonObjectCredit.put("amount", req.getMoneyToBeTransferred());
            destAccount.setBalance( destAccount.getAvailableBalance()+req.getMoneyToBeTransferred() );
            destAccount.setAvailableBalance( destAccount.getAvailableBalance()+req.getMoneyToBeTransferred() );
            log.info("available balance during money transfer in dest is {}" , destAccount.getAvailableBalance());
            jsonObjectCredit.put("transactionType", "CREDIT");
            ResponseEntity<String> apiResponseCredit = restConnector.sendPostRequest(moneyTransferUrl, jsonObjectCredit.toJSONString(), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
            if(!apiResponseCredit.getStatusCode().is2xxSuccessful()){
                throw new CreateTransactionException("Create transaction failed with response code: "+apiResponseCredit.getStatusCode());
            }
            accountRepository.save(destAccount);
            JSONObject jsonObjectDebit = new JSONObject();
            jsonObjectDebit.put("accountNo", req.getSourceAccountNumber());
            jsonObjectDebit.put("amount", req.getMoneyToBeTransferred());
            srcAccount.setBalance( srcAccount.getAvailableBalance() - req.getMoneyToBeTransferred() );
            srcAccount.setAvailableBalance( srcAccount.getAvailableBalance() - req.getMoneyToBeTransferred() );
            log.info("available balance during money transfer in src is {}" , srcAccount.getAvailableBalance());
            jsonObjectDebit.put("transactionType", "DEBIT");
            ResponseEntity<String> apiResponseDebit = restConnector.sendPostRequest(moneyTransferUrl, jsonObjectDebit.toJSONString(), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
            if(!apiResponseDebit.getStatusCode().is2xxSuccessful()){
                throw new CreateTransactionException("Create transaction failed with response code: "+apiResponseDebit.getStatusCode());
            }
            accountRepository.save(srcAccount);
        }
        return " ####MONEY TRANSFER SUCESSFULLY COMPLETED ###";
    }

    public Map<String, Object> getTransactions(String accountNumber) throws ParseException {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("TransAccount", "Account not found"));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accountNumber", account.getAccountNumber());
        responseMap.put("firstName", account.getCustomer().getFirstName());
        responseMap.put("lastName", account.getCustomer().getLastName());
        responseMap.put("availableBalance", account.getAvailableBalance());
        log.info("balance in getTransactions is  {} " , account.getAvailableBalance());
        String urlForGet =transactionServiceUrl ;
        String queryParam = "?accountNumber=".concat(accountNumber);
        urlForGet = urlForGet.concat(queryParam);
        String apiResponse = restConnector.sendGetRequest(urlForGet,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, new HttpHeaders()).getBody();
        JSONParser parser = new JSONParser();
        JSONArray json = (JSONArray) parser.parse(apiResponse);
        responseMap.put("transactions", json);
        return responseMap;

    }
}
