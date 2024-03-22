package com.cgi.accountservice.repository;

import com.cgi.accountservice.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByCustomerId(long customerId);
}
