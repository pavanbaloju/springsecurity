package com.pavanbaloju.springsecurity.bank.service;

import com.pavanbaloju.springsecurity.bank.entity.Customer;
import com.pavanbaloju.springsecurity.bank.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(Customer customer) {
        hashPassword(customer);
        customerRepository.save(customer);
    }

    private void hashPassword(Customer customer) {
        String hashedPwd = passwordEncoder.encode(customer.getPwd());
        customer.setPwd(hashedPwd);
    }
}
