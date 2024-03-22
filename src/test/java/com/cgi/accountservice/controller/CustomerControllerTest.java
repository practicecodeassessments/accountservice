package com.cgi.accountservice.controller;

import com.cgi.accountservice.config.JwtService;
import com.cgi.accountservice.data.dto.CreateCustomerRequest;
import com.cgi.accountservice.data.dto.MoneyTransferRequest;
import com.cgi.accountservice.data.entity.User;
import com.cgi.accountservice.model.AuthenticationRequest;
import com.cgi.accountservice.model.RegisterUserRequest;
import com.cgi.accountservice.service.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.cgi.accountservice.constants.Constants.*;
import static com.cgi.accountservice.controller.UserControllerTest.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JwtService jwtService;
    String jwttoken = "Bearer ";
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void intializeTestUser() {
        RegisterUserRequest userDummy = new RegisterUserRequest();
        userDummy.setUserName(user_name);
        userDummy.setPassword(pass_word);
        userDummy.setEmail(email);
        authenticationService.register(userDummy);

        User testuser = new User();
        testuser.setUserName(user_name);
        testuser.setPassword(pass_word);
        jwttoken += jwtService.generateToken(testuser);
    }



    @Test
    public void createCustomer_ifNotExists_ResultTrue() throws Exception {

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail(customer_email  );
        createCustomerRequest.setFirstName( customer_first_name );
        createCustomerRequest.setLastName( customer_last_name );
        createCustomerRequest.setPhone( customer_phone );
        createCustomerRequest.setAddress(customer_address);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(customer_create_url)
                        .header(HttpHeaders.AUTHORIZATION, jwttoken)
                        .content(asJsonString(createCustomerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void retrieve_Customer_Transactions() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(account_retrieve_url,accountNumber)
                        .header(HttpHeaders.AUTHORIZATION, jwttoken)
                        );
    }

    @Test
    public void perform_Money_Transfer() throws Exception {
        MoneyTransferRequest moneyTransferRequest = new MoneyTransferRequest();
        moneyTransferRequest.setSourceAccountNumber(sender_account_number );
        moneyTransferRequest.setDestinationAccountNumber(receiver_account_number);
        moneyTransferRequest.setMoneyToBeTransferred(amount_to_be_transferred);
        mockMvc.perform(MockMvcRequestBuilders
                .post(money_transfer_url)
                .header(HttpHeaders.AUTHORIZATION, jwttoken)
                .content(asJsonString(moneyTransferRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @AfterEach
    public void destroy() {

        AuthenticationRequest userDummy = new AuthenticationRequest();
        userDummy.setUserName(user_name);
        authenticationService.deleteUser(userDummy);
    }
}
