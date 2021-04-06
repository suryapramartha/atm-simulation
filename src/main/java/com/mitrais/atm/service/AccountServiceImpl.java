package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    private static final String CSV_SEPARATOR = ",";
    public AccountServiceImpl() { }

    @Override
    public Account getAccount(String accNumber, String accPin) {
        //List<Account> accounts = accountRepository.getAccounts();
        Predicate<Account> filterAccount = p ->
                p.getAccNumber().equalsIgnoreCase(accNumber) && p.getPin().equalsIgnoreCase(accPin);
        //return accounts.stream().filter(filterAccount).findFirst().get();
        return null;
    }

    @Override
    public Account getLoggedAccount() {
        return null;
    }

    @Override
    public void setLoggedAccount(Account account) {
            }

    @Override
    public List<Account> getAccountList() {
        return null;
    }

    @Override
    public void setAccountList(List<Account> accountList) {

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
