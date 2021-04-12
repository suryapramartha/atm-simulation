package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;

public interface TransactionService {
    Account processWithdraw(Transaction transaction, Account origin);
    Account processFundTransfer(Transaction transaction, Account origin, Account dest);
}
