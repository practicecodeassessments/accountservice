package com.cgi.accountservice.service;

import com.cgi.accountservice.config.JwtService;
import com.cgi.accountservice.data.entity.User;
import com.cgi.accountservice.model.AuthenticationRequest;
import com.cgi.accountservice.model.AuthenticationResponse;
import com.cgi.accountservice.model.RegisterUserRequest;
import com.cgi.accountservice.model.RegisterUserResponse;
import com.cgi.accountservice.repository.UserRepository;
import com.cgi.accountservice.util.Role;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is used for User specific functionality like authenticate,add and delete a user
 * to the applicaiton.
 *
 * @version 0.1
 * @author Rajesh Chanda
 *
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * This method used for register or create a new user to the application
   *
   * @param registerUserRequest - it takes the values for a new user information to register the application
   * @return it returns a string which holds the information of jwt token to authenticate the user
   */

  public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
    User user = User.builder()
        .userName(registerUserRequest.getUserName())
        .password(passwordEncoder.encode(registerUserRequest.getPassword()))
        .email(registerUserRequest.getEmail())
        .role(Role.USER)
        .build();
    repository.save(user);
    String jwtToken = jwtService.generateToken(user);
    RegisterUserResponse response = new RegisterUserResponse();
    response.setResponse(jwtToken);
    return response;

  }

  /**
   * This mehtod used for authenticate the user to the application before start any other functionality
   *
   * @param authenticationRequest - it takes the values for a  existing user
   * information like username and password.
   *
   * @return it returns a string which holds the information of jwt token to authenticate the user
   * or blank if it failed to authenticate
   */

  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUserName(),
            authenticationRequest.getPassword()
        )
    );

    Optional<User> user = repository.findByUserName(authenticationRequest.getUserName());
    String jwtToken = jwtService.generateToken(user.get());
    AuthenticationResponse response = new AuthenticationResponse();
    response.setResponse(jwtToken);
    return response;
  }

  /**
   * This method used for delete a user from the applicaiton
   *
   * @param authenticationRequest - it takes the values for a  existing user
   * information like username
   * @return it returns a string which give information about the delete status
   */

  @Transactional
  public AuthenticationResponse deleteUser(AuthenticationRequest authenticationRequest) {
    repository.deleteByUserName(authenticationRequest.getUserName());
    AuthenticationResponse response = new AuthenticationResponse();
    response.setResponse("User Deleted");
    return response;

  }
}
