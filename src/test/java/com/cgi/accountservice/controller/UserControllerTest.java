package com.cgi.accountservice.controller;

import com.cgi.accountservice.model.AuthenticationRequest;
import com.cgi.accountservice.model.AuthenticationResponse;
import com.cgi.accountservice.model.RegisterUserRequest;
import com.cgi.accountservice.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.cgi.accountservice.constants.Constants.STATUS_400;
import static com.cgi.accountservice.constants.Constants.authenticate_url;
import static com.cgi.accountservice.constants.Constants.email;
import static com.cgi.accountservice.constants.Constants.pass_word;
import static com.cgi.accountservice.constants.Constants.register_url;
import static com.cgi.accountservice.constants.Constants.user_name;
import static com.cgi.accountservice.constants.Constants.user_remove_url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void register_newUser_Success() throws Exception {

        when(authenticationService.register(any())).thenReturn(any());

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName(user_name);
        registerUserRequest.setPassword(pass_word);
        registerUserRequest.setEmail(email);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(register_url)
                        .content(asJsonString(registerUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void authenticate_nonExistUser_statusFailure() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUserName(user_name);
        authenticationRequest.setPassword(pass_word);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setResponse(STATUS_400);
        lenient().when(userController.authenticate(authenticationRequest)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(authenticate_url)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals( mvcResult.getResponse().getContentAsString(), "");

    }

    @Test
    public void remove_User_if_exists_from_System() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUserName(user_name);
        authenticationRequest.setPassword(pass_word);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setResponse(STATUS_400);
        lenient().when(userController.authenticate(authenticationRequest)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(user_remove_url)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals( mvcResult.getResponse().getContentAsString(), "");

    }
}
