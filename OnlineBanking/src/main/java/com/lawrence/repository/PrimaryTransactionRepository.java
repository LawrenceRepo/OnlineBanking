package com.lawrence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.model.PrimaryTransaction;

public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();
}
