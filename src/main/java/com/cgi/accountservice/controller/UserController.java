package com.cgi.accountservice.controller;

import com.cgi.accountservice.model.AuthenticationRequest;
import com.cgi.accountservice.model.AuthenticationResponse;
import com.cgi.accountservice.model.RegisterUserRequest;
import com.cgi.accountservice.model.RegisterUserResponse;
import com.cgi.accountservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * This resource class serves the endpoint for user related functionality
 *
 * @author Rajesh Chanda
 * @version 0.1
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Getter
@Setter
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

  @Autowired
  private final AuthenticationService service;

  /**
   * This endpoint used for register a new user.
   *
   * @param registerUserRequest - it takes the user information
   * @return registerUserResponse - return a string information about the status
   */

  @PostMapping("/register")
  public ResponseEntity<RegisterUserResponse> register(
      @RequestBody RegisterUserRequest registerUserRequest
  ) {
    return ResponseEntity.ok(service.register(registerUserRequest));
  }

  /**
   * This endpoint used for authenticate a  user to the application
   *
   * @param authenticationRequest - it takes the user information for authenticate
   * @return registerUserResponse - return a string information about the authenticate status
   */

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest authenticationRequest
  ) {
    return ResponseEntity.ok(service.authenticate(authenticationRequest));
  }

  /**
   * This endpoint used for delete  a  user from the application
   *
   * @param authenticationRequest - it takes the user information for delete
   * @return registerUserResponse - return a string information about the delete status
   */

  @PostMapping("/remove")
  public ResponseEntity<AuthenticationResponse> remove(
      @RequestBody AuthenticationRequest authenticationRequest
  ) {
    return ResponseEntity.ok(service.deleteUser(authenticationRequest));
  }


}
