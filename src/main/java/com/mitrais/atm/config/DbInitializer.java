package com.mitrais.atm.config;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DbInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Bean
    public ApplicationRunner initializer(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        List<Account> accounts = generateAccounts(200);
        List<Transaction> transactions = generateTransactions(10);

        return args -> {
                accountRepository.saveAll(accounts);
                transactionRepository.saveAll(transactions);
            };
    }


    private List<Account> generateAccounts(int total) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i <= total; i++) {
            int generatedID = 100000 + i;
            Account account = new Account();
            account.setAccNumber(String.valueOf(generatedID));
            account.setPin((String.valueOf(generatedID)));
            account.setBalance(100);
            account.setName("account_"+i);

            Optional<Account> existingAccount = accountRepository.findById(String.valueOf(generatedID));
            if (!existingAccount.isPresent())
                accounts.add(account);
        }
        return accounts;
    }

    private List<Transaction> generateTransactions(int total) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i <= total; i++) {
            int generatedID = 100000+i;
            Transaction transaction = new Transaction();
            transaction.setTransactionId(i);
            transaction.setAccountNumber(String.valueOf(generatedID));
            transaction.setBalance(100);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionType("WITHDRAW");
            transaction.setAmount("50");
            transaction.setRefNo("999999");

            Optional<Transaction> existingTransaction = transactionRepository.findById(generatedID);
            if (!existingTransaction.isPresent())
                transactions.add(transaction);
        }
        return transactions;
    }
}
