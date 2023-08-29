package com.pavanbaloju.springsecurity.bank.service;

import com.pavanbaloju.springsecurity.bank.entity.Customer;
import com.pavanbaloju.springsecurity.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomerRepository customerRepository;

    public LoginService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createUser(Customer customer) {
        customerRepository.save(customer);
    }
}
