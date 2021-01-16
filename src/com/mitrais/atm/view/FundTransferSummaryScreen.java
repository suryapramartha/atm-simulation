package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;

import java.util.Scanner;

public class FundTransferSummaryScreen implements Screen{

    Account destAcc = new Account();
    String amount;
    String refNo;

    public FundTransferSummaryScreen(Account destAcc, String amount, String refNo) {
        this.destAcc = destAcc;
        this.amount = amount;
        this.refNo = refNo;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Fund Transfer Summary=======");
        System.out.println("Destination Account : "+ destAcc.getAccNumber());
        System.out.println("Transfer Amount : $"+ amount);
        System.out.println("Reference Number : "+ refNo);
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
