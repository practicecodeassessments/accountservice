package com.cgi.accountservice.event.listener;


import com.cgi.accountservice.data.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.cgi.accountservice.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContextEventListener {

    private final CustomerRepository customerRepository;

    @EventListener
    public void handleContextStartEvent(ContextStartedEvent ctxStartEvt) {
        System.out.println("Prepopulating customers table with basic details");

        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer("Rajesh", "Ch","rch@gmail.com", "1325 EF", "0685515553", 100001);
        customers.add(customer1);
        Customer customer2 = new Customer("Prasanna", "Ch","pch@gmail.com", "1325 AB", "0685515552", 100002);
        customers.add(customer2);
        Customer customer3 = new Customer("Aarnavi", "Ch","ach@email.com", "1325 CD", "0685515551", 100003);
        customers.add(customer3);

        customerRepository.saveAll(customers);

    }
}
