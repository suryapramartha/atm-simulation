package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Account processWithdraw(Transaction transaction, Account origin) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(transaction.getAmount()));
        accountRepository.save(origin);
        transactionRepository.save(transaction);
        return origin;
    }

    @Override
    public Account processFundTransfer(Transaction transaction, Account origin, Account dest) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(transaction.getAmount()));
        dest.setBalance(dest.getBalance() + Integer.parseInt(transaction.getAmount()));
        accountRepository.save(origin);
        accountRepository.save(dest);
        transactionRepository.save(transaction);
        return origin;
    }

    @Override
    public List<Transaction> getTransactionHistory(String accNumber, int limit) {
        return transactionRepository.findByAccountNumberOrderByTransactionDateDesc(accNumber).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionHistoryOnDate(String accNumber, LocalDate date, int limit) {
        return transactionRepository.findByAccountNumberAndTransactionDateOrderByTransactionDateDesc(accNumber,date).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

}
