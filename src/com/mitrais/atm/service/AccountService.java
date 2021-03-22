package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> loadAccountsFromCSV(String path) throws Exception;
    Account getAccount(String accNumber, String accPin);
    Account getLoggedAccount();
    void setLoggedAccount(Account account);
    List<Account> getAccountList();
}
