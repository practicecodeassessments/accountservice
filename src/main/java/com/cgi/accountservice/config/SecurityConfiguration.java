package com.cgi.accountservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This configuration class is  related to define the security for the application.
 * It defines the SecurityFilter chaining object of Spring framework to define the security steps.
 *
 * @author Rajesh Chanda
 * @version 0.1
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/v1/auth/**", "/api-docs/**", "/swagger-ui/**","/h2-console/**","/actuator/**" };

  /**
   *  This method tailed the security rule for the application using SecurityFilter chain
   * @param http -
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf().disable()
        .headers().frameOptions().disable()
      ;

    return http.build();

  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

}
