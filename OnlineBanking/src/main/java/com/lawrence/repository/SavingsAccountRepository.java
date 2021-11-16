package com.lawrence.repository;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.model.SavingsAccount;


public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {

    SavingsAccount findByAccountNumber (int accountNumber);
}
