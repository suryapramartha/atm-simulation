package com.mitrais.atm.repository;

import com.mitrais.atm.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByAccountNumberOrderByTransactionDateDesc(String accNumber);
    List<Transaction> findByAccountNumberAndTransactionDateOrderByTransactionDateDesc(String accNumber,LocalDate date);

}
