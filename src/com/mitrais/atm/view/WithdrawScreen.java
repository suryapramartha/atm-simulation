package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class WithdrawScreen implements Screen{

    TransactionService transactionService = new TransactionService();

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====Withdraw Screen=====");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                transactionService.processWithdraw(10);
            case "2":
                transactionService.processWithdraw(50);
            case "3":
                transactionService.processWithdraw(100);
            case "4":
                OtherWithdrawScreen otherWithdrawScreen = new OtherWithdrawScreen();
                otherWithdrawScreen.showScreen();
            case "5": case "":
                TransactionScreen transactionScreen = new TransactionScreen();
                transactionScreen.showScreen();
            default:
                this.showScreen();
        }
    }
}
