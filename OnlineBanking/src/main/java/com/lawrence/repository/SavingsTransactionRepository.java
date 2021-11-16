package com.lawrence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.model.SavingsTransaction;

public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Long> {

    List<SavingsTransaction> findAll();
}

