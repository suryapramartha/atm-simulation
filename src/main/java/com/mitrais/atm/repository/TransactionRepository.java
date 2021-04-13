package com.mitrais.atm.repository;

import com.mitrais.atm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findByAccountNumber(String accNumber);
    List<Transaction> findByAccountNumberAndTransactionDate(String accNumber,LocalDate date);
}