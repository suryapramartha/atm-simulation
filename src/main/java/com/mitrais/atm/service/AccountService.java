package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;

public interface AccountService {
    Account getAccount(String accNumber, String accPin);
    Account getLoggedAccount();
    void setLoggedAccount(Account account);

}
