package com.pavanbaloju.springsecurity.bank.repository;

import com.pavanbaloju.springsecurity.bank.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
