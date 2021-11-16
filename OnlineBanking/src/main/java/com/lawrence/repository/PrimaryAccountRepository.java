package com.lawrence.repository;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.model.PrimaryAccount;

public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount,Long> {

    PrimaryAccount findByAccountNumber (int accountNumber);
}
