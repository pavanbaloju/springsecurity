package com.pavanbaloju.springsecurity.bank.repository;

import com.pavanbaloju.springsecurity.bank.entity.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    //    @PreFilter, @PostFilter, @PostAuthorize are also available
    @PreAuthorize("hasRole('USER')")
    Accounts findByCustomerId(int customerId);

}
