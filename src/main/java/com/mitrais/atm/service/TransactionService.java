package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    Map<String, Object> processWithdraw(String amount) throws Exception;
    Map<String,Object> processFundTransfer(String accDest, String amount) throws Exception;
    List<Transaction> getTransactionHistory(String accNumber, int limit);
    List<Transaction> getTransactionHistoryOnDate(String accNumber, LocalDate date, int limit);
}
