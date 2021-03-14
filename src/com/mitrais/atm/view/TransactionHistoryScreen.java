package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionHistoryScreen implements Screen{

    Account account = null;
    List<Account> accounts = null;
    Transaction transaction = new Transaction();
    List<Transaction> transactions = transaction.getTransactions();

    public TransactionHistoryScreen(Account account, List<Account> accounts) {
        this.account = account;
        this.accounts = accounts;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);        System.out.println("=======Transaction History Screen=======");
        System.out.println("Name : "+account.getName());
        System.out.println("Account Number : "+account.getAccNumber());
        System.out.println("Date : "+new Date());
        System.out.println("=========================================");
        System.out.println("Date | Transaction Type | Amount | Balance");
        transactions.stream()
                .filter(c -> c.getAccountNumber().equalsIgnoreCase(account.getAccNumber()))
                .limit(10)
                .forEach(p -> {
                    System.out.print(p.getTransactionDate()+" | ");
                    System.out.print(p.getTransactionType()+" | ");
                    System.out.print(p.getAmount()+" | ");
                    System.out.println(p.getBalance());
                });

        System.out.println("=========================================");
        System.out.println("1. Transaction Screen");
        System.out.println("2. Exit");
        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": case "2": case "":
                TransactionScreen transactionScreen = new TransactionScreen(account, accounts);
                transactionScreen.showScreen();
            default:
                this.showScreen();
        }
    }
}
