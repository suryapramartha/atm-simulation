package com.mitrais.atm.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Transaction {
    private String accountNumber;
    private String transactionType;
    private Date transactionDate;
    private String amount;
    private int balance;

    private static List<Transaction> transactions = new ArrayList<>();

    public Transaction() {
    }

    public Transaction(String accountNumber, String transactionType, Date transactionDate, String amount, int balance) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addTransaction(Account origin, String transactionType, String amount) {
        Transaction newTransaction = new Transaction(origin.getAccNumber(), transactionType, new Date(), amount, origin.getBalance());
        this.transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public List<Transaction> getLastTenTransactions(String accountNumber) {
        return this.transactions.stream()
                .filter(c -> c.getAccountNumber().equalsIgnoreCase(accountNumber))
                .limit(10).sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(Collectors.toList());
    };

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", amount='" + amount + '\'' +
                ", balance=" + balance +
                '}';
    }
}
