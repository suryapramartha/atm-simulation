package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionHistoryScreen implements Screen{

    Account account = null;
    List<Account> accounts = null;

    public TransactionHistoryScreen(Account account, List<Account> accounts) {
        this.account = account;
        this.accounts = accounts;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        UserData userData = new UserData();
        List<List<String>> data = userData.getTransactionHistory(account.getAccNumber());
        System.out.println("=======Transaction History Screen=======");
        System.out.println("Name : "+account.getName());
        System.out.println("Account Number : "+account.getAccNumber());
        System.out.println("Date : "+new Date());
        System.out.println("=========================================");
        System.out.println("Date | Transaction Type | Amount | Balance");
        data.stream().forEach(p -> {
            System.out.print(p.get(1)+" | ");
            System.out.print(p.get(2)+" | ");
            System.out.print(p.get(3)+" | ");
            System.out.println(p.get(4));
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
