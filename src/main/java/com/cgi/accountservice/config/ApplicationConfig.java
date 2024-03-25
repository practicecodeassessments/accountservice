package com.cgi.accountservice.config;

import com.cgi.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * This configuration class is used to configure and intialize the security configuration object
 *
 * @author Rajesh Chanda
 * @version 0.1
 */

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * This method overrides UserDetailService of Security default behaviour.
     * It uses custom JPA repository to retrieve the user inforamtion from users table.
     * @return Either user information or User not Found Exception
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the platform"));
    }


    /**
     * This method intialize the Authenticate provider of spring security framework to override the default daoAthenticateprovider.
     * The DaoAuthentication provider use the custom user service provider i.e. userDetailsService
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Authertication Manager object returned from config
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Utility class for passwrodEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedOrigins(Arrays.asList("localhost:8089"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
