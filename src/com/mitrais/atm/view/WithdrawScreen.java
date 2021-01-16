package com.mitrais.atm.view;

import com.mitrais.atm.service.TransactionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WithdrawScreen implements Screen{

    TransactionService transactionService = new TransactionService();

    @Override
    public void showScreen() {

        Map<String,Object> result = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("=====Withdraw Screen=====");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            transactionService.processWithdraw(10);
        }else if(choice.equalsIgnoreCase("2")){
            transactionService.processWithdraw(50);
        }else if(choice.equalsIgnoreCase("3")){
            transactionService.processWithdraw(100);
        }else if(choice.equalsIgnoreCase("4")){
            OtherWithdrawScreen otherWithdrawScreen = new OtherWithdrawScreen();
            otherWithdrawScreen.showScreen();
        }
        else if(choice.equalsIgnoreCase("5")){
            TransactionScreen transactionScreen = new TransactionScreen();
            transactionScreen.showScreen();
        }
        else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2") ||
                choice.equalsIgnoreCase("3") ||
                choice.equalsIgnoreCase("4") ||
                choice.equalsIgnoreCase("5")) && !choice.isEmpty()) {
            this.showScreen();
        }else {
            TransactionScreen transactionScreen = new TransactionScreen();
            transactionScreen.showScreen();
        }
    }
}
