package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;

import java.util.List;
import java.util.Scanner;

public class TransactionScreen implements Screen{

    Account account = null;
    List<Account> accounts = null;

    public TransactionScreen(Account account, List<Account> accounts) {
        this.accounts = accounts;
        this.account = account;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====Transaction Screen=====");
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Transaction History");
        System.out.println("4. Exit");
        System.out.print("Please choose option[4]: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                WithdrawScreen withdrawScreen = new WithdrawScreen(account, accounts);
                withdrawScreen.showScreen();
            case "2":
                FundTransferScreen fundTransferScreen = new FundTransferScreen(account, accounts);
                fundTransferScreen.showScreen();
            case "3":
                TransactionHistoryScreen transactionHistoryScreen = new TransactionHistoryScreen(account, accounts);
                transactionHistoryScreen.showScreen();
            case "4": case "":
                WelcomeScreen welcomeScreen = new WelcomeScreen(accounts);
                welcomeScreen.showScreen();
            default:
                this.showScreen();
        }
    }
}
