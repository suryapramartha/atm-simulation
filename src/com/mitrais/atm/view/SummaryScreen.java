package com.mitrais.atm.view;

import com.mitrais.atm.util.UserData;

import java.util.Date;
import java.util.Scanner;

public class SummaryScreen implements Screen {

    int withdraw;

    public SummaryScreen(int withdraw){
        this.withdraw = withdraw;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Summary Screen=======");
        System.out.println(new Date());
        System.out.println("Withdraw : $"+withdraw);
        System.out.println("Balance : $"+ UserData.loggedAccount.getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            TransactionScreen transactionScreen = new TransactionScreen();
            transactionScreen.showScreen();
        }else if(choice.equalsIgnoreCase("2")){
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.showScreen();
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                && !choice.isEmpty()) {
            this.showScreen();
        }else {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.showScreen();
        }
    }
}
