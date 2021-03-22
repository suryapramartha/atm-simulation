package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.AccountServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SummaryScreen implements Screen {

    AccountServiceImpl accountService = new AccountServiceImpl();
    int withdraw;

    public SummaryScreen(int withdraw)
    {
        this.withdraw = withdraw;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Summary Screen=======");
        System.out.println(new Date());
        System.out.println("Withdraw : $"+withdraw);
        System.out.println("Balance : $"+ accountService.getLoggedAccount().getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": case "2": case "":
                TransactionScreen transactionScreen = new TransactionScreen();
                transactionScreen.showScreen();
            default:
                this.showScreen();
        }
    }
}
