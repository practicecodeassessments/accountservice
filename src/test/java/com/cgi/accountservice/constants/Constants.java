package com.cgi.accountservice.constants;

public interface Constants {
    String user_name = "apiuser";
    String pass_word = "apiuser123";
    String email = "apiuser123@carplatform.com";
    String STATUS_400 = "Bad Request" ;
    String register_url = "/api/v1/auth/register";
    String authenticate_url = "/api/v1/auth/authenticate" ;
    String user_remove_url = "/api/v1/auth/remove" ;
    String customer_email= "customer1insystem@bank.com";
    String customer_address= "customer1address";
    String customer_phone= "123456789";
    String customer_first_name= "CFirstName";
    String customer_last_name= "CLastName";
    String account_create_url = "/api/v1/customer/create-account";
    String accountNumber = "12345678";
    String account_retrieve_url = "/api/v1/customer/{accountNumber}";
    String customer_create_url = "/api/v1/customer/create-customer" ;
    String money_transfer_url = "/api/v1/customer/money-transfer" ;
    String sender_account_number ="12345678";
    String receiver_account_number ="23456789";
    double amount_to_be_transferred = 1000;

}
