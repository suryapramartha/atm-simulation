package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.view.TransactionScreen;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DataValidation {

    public DataValidation() {}

    public boolean checkAccountNumberCredential(String accNumber) {
        boolean isValid = true;
        if(accNumber.length() != 6){
            System.out.println("Account Number should have 6 digits length");
            isValid = false;
        }
        else if(!accNumber.matches("[0-9]+")){
            System.out.println("Account Number should only contains numbers");
            isValid = false;
        }
        return isValid;
    }
    public boolean checkPIN(String accPin) {
        boolean isValid = true;
        if(accPin.length() != 6){
            System.out.println("PIN should have 6 digits length");
            isValid = false;
        }
        else if(!accPin.matches("[0-9]+")){
            System.out.println("PIN should only contains numbers");
            isValid = false;
        }
        return isValid;
    }

    public Account checkLoginCredential(String accNumber, String accPin, List<Account> accounts) {
        Account account = null;
        Predicate<Account> filterAccount = p ->
                p.getAccNumber().equalsIgnoreCase(accNumber) && p.getPin().equalsIgnoreCase(accPin);
        Optional<Account> result = accounts.stream().filter(filterAccount).findFirst();

        if(result.isPresent()) {
            account = result.get();
        }else {
            System.out.println("Invalid Account Number/PIN");
        }
        return account;
    }

    public String checkWithdrawAmount(String amount) {
        String errorMessage = null;

        if(!amount.matches("[0-9]+")){
            errorMessage = "Invalid amount";
        }else {
            int amountNumb = Integer.valueOf(amount);
            if(amountNumb > 1000) {
                errorMessage = "Maximum amount to withdraw is $1000";
            }else if (amountNumb%10 != 0) {
                errorMessage = "Invalid amount";
            }
        }
        return errorMessage;
    }

    public Account checkFundInputData(String dest, String amount, Account loggedAccount ,List<Account> accounts) {
        Account account = null;
        boolean isAccountExist = false;
        boolean isError = false;

        Predicate<Account> filterAccount = p ->
                !p.getAccNumber().equalsIgnoreCase(loggedAccount.getAccNumber()) && p.getAccNumber().equalsIgnoreCase(dest);
        Optional<Account> result = accounts.stream().filter(filterAccount).findFirst();
        if(result.isPresent()) {
            isAccountExist = true;
            account = result.get();
        }

        if(!dest.matches("[0-9]+")) {
            System.out.println("Invalid account");
            isError = true;
        } else {
            if(!isAccountExist) {
                System.out.println("Invalid account");
                isError = true;
            } else if(!amount.matches("[0-9]+")) {
                System.out.println("Invalid amount");
                isError = true;
            }else {
                if(amount.length() > 10) {
                    amount = amount.substring(1,10);
                }
                int amountNumb = Integer.valueOf(amount);
                if(amountNumb > 1000) {
                    System.out.println("Maximum amount to withdraw is $1000");
                    isError = true;
                }else if (amountNumb<1) {
                    System.out.println("Minimum amount to withdraw is $1");
                    isError = true;
                }else if(amountNumb > loggedAccount.getBalance()) {
                    System.out.println("Insufficient balance $"+amountNumb);
                    isError = true;
                }
            }
        }
        if(isError) {
            TransactionScreen transactionScreen = new TransactionScreen(loggedAccount, accounts);
            transactionScreen.showScreen();
        }
        return account;
    }
}
