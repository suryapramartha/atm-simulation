package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;

import java.util.List;

public class AccountRepository {
    private static List<Account> accounts;
    private static Account loggedAccount;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getLoggedAccount() {
        return loggedAccount;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }
}
