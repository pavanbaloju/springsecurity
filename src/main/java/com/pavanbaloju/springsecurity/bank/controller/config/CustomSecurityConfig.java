package com.pavanbaloju.springsecurity.bank.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class CustomSecurityConfig {

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myLoans", "/myBalance", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact").permitAll())
            .formLogin(withDefaults()) // to allow api requests with UI login
            .httpBasic(withDefaults()) // to allow api requests with login from say postman
            .build();
    }
}
