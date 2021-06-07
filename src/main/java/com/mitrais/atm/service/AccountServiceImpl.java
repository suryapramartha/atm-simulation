package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private static Account loggedAccount;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DataValidationService dataValidationService;

    private static final String CSV_SEPARATOR = ",";

    @Override
    public Account getAccount(String accNumber, String accPin) throws Exception {
        dataValidationService.checkLoginCredential(accNumber, accPin);

        return accountRepository.findByAccNumberAndPin(accNumber, accPin);
    }

    @Override
    public Account getLoggedAccount() {
        return loggedAccount;
    }

    @Override
    public void setLoggedAccount(Account account) {
        loggedAccount = account;
    }

    @Override
    public Account getAccountByAccNumber(String accNumber) throws Exception {
        Optional<Account> existingAcc = accountRepository.findById(accNumber);
        if(!existingAcc.isPresent()) {
            throw new Exception("Data not found");
        }
        return existingAcc.get();
    }

    private Function<List<String>, Account> mapToAccount = line -> {
      Account account = new Account();
      account.setName(line.get(0));
      account.setPin(line.get(1));
      account.setBalance(Integer.valueOf(line.get(2)));
      account.setAccNumber(line.get(3));
      return account;
    };

    private List<Account> filterDuplicateAccount(List<Account> data) {
        List<Account> result =  data.stream()
                .distinct()
                .collect(Collectors.toList());
        if (result.size() < data.size())
             System.out.println("Error : Duplicate Account Number on CSV file!");
        return result;
    };
}
