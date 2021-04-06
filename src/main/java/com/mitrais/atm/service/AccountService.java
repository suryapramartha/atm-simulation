package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;

import java.util.List;

public interface AccountService {
    Account getAccount(String accNumber, String accPin);

    Account getLoggedAccount();
    void setLoggedAccount(Account account);

    List<Account> getAccountList();
    void setAccountList(List<Account> accountList);
}
