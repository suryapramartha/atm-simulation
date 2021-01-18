package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;

import java.util.List;
import java.util.Scanner;

public class WelcomeScreen implements Screen{

    List<Account> accounts = null;
    Scanner scanner = new Scanner(System.in);

    DataValidation validation = new DataValidation();

    public WelcomeScreen(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void showScreen() {
        System.out.println("======================================");
        System.out.println("=====Welcome to ATM Simulation v1=====");
        System.out.println("=============Please login=============");
        System.out.print("Enter Account Number : ");
        String accNumber = scanner.nextLine();
        boolean isAccNumberValid = validation.checkAccountNumberCredential(accNumber);

        if (isAccNumberValid) {
            System.out.print("Enter PIN : ");
            String accPin = scanner.nextLine();
            boolean isPinValid = validation.checkPIN(accPin);
            if (isPinValid) {
                Account account = validation.checkLoginCredential(accNumber, accPin, accounts);
                if(account != null) {
                    TransactionScreen transactionScreen = new TransactionScreen(account,accounts);
                    transactionScreen.showScreen();
                }
            }
        }
    }
}
