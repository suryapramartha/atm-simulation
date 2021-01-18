package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SummaryScreen implements Screen {

    int withdraw;
    Account account = null;
    List<Account> accounts = null;

    public SummaryScreen(int withdraw, Account account, List<Account> accounts)
    {
        this.withdraw = withdraw;
        this.account = account;
        this.accounts = accounts;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Summary Screen=======");
        System.out.println(new Date());
        System.out.println("Withdraw : $"+withdraw);
        System.out.println("Balance : $"+ account.getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            TransactionScreen transactionScreen = new TransactionScreen(account, accounts);
            transactionScreen.showScreen();
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                && !choice.isEmpty()) {
            this.showScreen();
        }
    }
}
