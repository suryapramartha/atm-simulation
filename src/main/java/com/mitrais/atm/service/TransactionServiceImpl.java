package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account processWithdraw(Transaction transaction, Account origin) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(transaction.getAmount()));
        accountRepository.save(origin);
        transactionRepository.save(transaction);
        return origin;
    }

    @Override
    @Transactional
    public Account processFundTransfer(Transaction transaction, Account origin, Account dest) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(transaction.getAmount()));
        dest.setBalance(dest.getBalance() + Integer.parseInt(transaction.getAmount()));
        accountRepository.save(origin);
        accountRepository.save(dest);
        transactionRepository.save(transaction);
        return origin;
    }

    @Override
    public List<Transaction> getTransactionHistory(String accNumber) {
        return transactionRepository.findByAccountNumber(accNumber);
    }

    @Override
    public List<Transaction> getTransactionHistoryOnDate(String accNumber, LocalDate date) {
        return transactionRepository.findByAccountNumberAndTransactionDate(accNumber,date);
    }
}
