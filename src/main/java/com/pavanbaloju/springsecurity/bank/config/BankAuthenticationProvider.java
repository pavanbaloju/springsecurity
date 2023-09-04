package com.pavanbaloju.springsecurity.bank.config;

import com.pavanbaloju.springsecurity.bank.entity.Customer;
import com.pavanbaloju.springsecurity.bank.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BankAuthenticationProvider implements AuthenticationProvider {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public BankAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        Customer customer = getCustomer(username);

        if (passwordEncoder.matches(pwd, customer.getPwd())) {
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = customer.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).toList();
            return new UsernamePasswordAuthenticationToken(username, pwd, simpleGrantedAuthorities);
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    private Customer getCustomer(String username) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
        return customerOptional.orElseThrow(() -> new BadCredentialsException("No user found with the given email"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
