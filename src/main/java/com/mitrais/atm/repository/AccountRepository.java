package com.mitrais.atm.repository;

import com.mitrais.atm.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
