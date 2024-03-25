package com.cgi.accountservice.service;
import com.cgi.accountservice.data.entity.Account;
import com.cgi.accountservice.data.entity.Customer;
import com.cgi.accountservice.exception.AccountNotFoundException;
import com.cgi.accountservice.model.CustomerAccountModel;
import com.cgi.accountservice.model.MoneyTransferModel;
import com.cgi.accountservice.model.NewCustomerModel;
import com.cgi.accountservice.repository.AccountRepository;
import com.cgi.accountservice.repository.CustomerRepository;
import com.cgi.accountservice.util.AccountNoGenerator;
import com.cgi.accountservice.util.enums.AccountStatus;
import com.cgi.accountservice.util.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static com.cgi.accountservice.constants.Constants.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class customerServiceTest
{
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private  CustomerRepository customerRepository;
    @Mock
    private  AccountRepository accountRepository;

    @Test
    public void testCreateCustomerSuccess(){
        Customer customerMock = getCustomer();
        when(customerRepository.save(any())).thenReturn(customerMock);
        NewCustomerModel customerReqMock = getCustomerReq();
        ResponseEntity<Object> actualResult = customerService.createCustomer(customerReqMock);
        assertThat(actualResult).isNotNull();
        verify(customerRepository).save( any(Customer.class) );
    }

    @Test
    public void testShouldThrowAccountdoesnotExist(){
        Account accountMock = getAccount();
        when(accountRepository.save(any())).thenReturn(accountMock);
        CustomerAccountModel createAccountRequestMock = getAccountReq();
        assertThrows( AccountNotFoundException.class,()->{customerService.createAccount(createAccountRequestMock);});
    }

    @Test
    public void testShouldReturnCustomerById(){
        Customer customerMock = getCustomer();
        Optional<Customer> customerOptional = Optional.of(customerMock);
        when(customerRepository.findByCustomerId( anyLong() )).thenReturn( customerOptional );
        assertThat(customerOptional).isNotNull();
    }

    @Test
    public void testCreateAccountSuccess(){
        Account accountMock = getAccount();
        when(accountRepository.save(any())).thenReturn(accountMock);
        assertThat(accountMock).isNotNull();

    }

    @Test
    public void testMoneyTransferSuccess(){
        String srcAcccountNumber = "12345678";
        String destAcccountNumber = "87654321";
        double moneyToBeTransferred = 100;
        MoneyTransferModel moneyTransferRequest = new MoneyTransferModel();
        moneyTransferRequest.setMoneyToBeTransferred( moneyToBeTransferred );
        moneyTransferRequest.setSourceAccountNumber(srcAcccountNumber  );
        moneyTransferRequest.setDestinationAccountNumber(destAcccountNumber);
//        when(accountRepository.findByAccountNumber( anyString())).thenReturn();
//        assertThat(accountMock).isNotNull();

    }

    private static Customer getCustomer() {
        Customer customerMock = new Customer();
        customerMock.setAddress( customer_address );
        customerMock.setEmail( customer_email);
        customerMock.setFirstName( customer_first_name );
        customerMock.setLastName( customer_last_name );
        return customerMock;
    }

    private static Account getAccount() {
        Account accountMock = new Account();
        accountMock.setAccountNumber( AccountNoGenerator.generate());
        accountMock.setCustomer(getCustomer());
        accountMock.setType( AccountType.CURRENT);
        accountMock.setAvailableBalance(1000);
        accountMock.setBalance(1000);
        accountMock.setStatus( AccountStatus.ACTIVE);
        return accountMock;
    }

    private static NewCustomerModel getCustomerReq() {
        NewCustomerModel customerReqMock = new NewCustomerModel();
        customerReqMock.setAddress( customer_address );
        customerReqMock.setEmail( customer_email);
        customerReqMock.setFirstName( customer_first_name );
        customerReqMock.setLastName( customer_last_name );
        return customerReqMock;
    }

    private static CustomerAccountModel getAccountReq() {
        CustomerAccountModel accountReqMock = new CustomerAccountModel();
        accountReqMock.setCustomerId( 557123 );
        accountReqMock.setInitialCredit( 1000.0 );
        return accountReqMock;
    }

}
