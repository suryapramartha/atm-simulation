package com.mitrais.atm.config;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DbInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public ApplicationRunner initializer(AccountRepository repository) {
        List<Account> accounts = generateAccounts(200);

        return args -> repository.saveAll(accounts);
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
}
