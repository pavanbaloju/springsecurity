package com.pavanbaloju.springsecurity.bank.repository;

import com.pavanbaloju.springsecurity.bank.entity.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    Accounts findByCustomerId(int customerId);

}
