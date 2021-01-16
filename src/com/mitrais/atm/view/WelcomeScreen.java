package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.UserData;

import java.util.Map;
import java.util.Scanner;

public class WelcomeScreen implements Screen{

    Scanner scanner = new Scanner(System.in);

    DataValidation validation = new DataValidation();
    TransactionScreen transactionScreen = new TransactionScreen();

    @Override
    public void showScreen() {
        String errorMessage = null;
        Map<String, Object> res;
        System.out.println("======================================");
        System.out.println("=====Welcome to ATM Simulation v1=====");

        System.out.println("=============Please login=============");
        System.out.print("Enter Account Number : ");
        String accNumber = scanner.nextLine();
        System.out.print("Enter PIN : ");
        String accPin = scanner.nextLine();
        res = validation.checkLoginCredential(accNumber, accPin);
        Boolean loggedIn = (Boolean) res.get("isLoggedIn");
        if (res.get("errorMessage") != null) {
            errorMessage = res.get("errorMessage").toString();
            System.out.println("Error : " + errorMessage);
        } else {
            UserData.loggedAccount = (Account) res.get("accountData");
            transactionScreen.showScreen();
        }
    }
}
