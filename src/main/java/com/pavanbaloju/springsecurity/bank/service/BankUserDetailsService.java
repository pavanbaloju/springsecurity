package com.pavanbaloju.springsecurity.bank.service;

import com.pavanbaloju.springsecurity.bank.entity.Customer;
import com.pavanbaloju.springsecurity.bank.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

//@Service
public class BankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public BankUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
        if (customerOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found for given email: " + username);
        }
        Customer customer = customerOptional.get();
        return new User(customer.getEmail(), customer.getPwd(), Collections.singleton(new SimpleGrantedAuthority(customer.getRole())));
    }
}
