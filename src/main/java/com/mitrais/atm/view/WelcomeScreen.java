package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.service.AccountServiceImpl;
import com.mitrais.atm.service.DataValidationServiceImpl;

import java.util.Scanner;

public class WelcomeScreen implements Screen{
    Scanner scanner = new Scanner(System.in);

    DataValidationServiceImpl validation = new DataValidationServiceImpl();
    AccountServiceImpl accountService = new AccountServiceImpl();

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
                boolean isValid = true;
                if(isValid) {
                    Account account = accountService.getAccount(accNumber, accPin);
//                    TransactionScreen transactionScreen = new TransactionScreen();
//                    transactionScreen.showScreen();
                }
            }
        }
    }
}
