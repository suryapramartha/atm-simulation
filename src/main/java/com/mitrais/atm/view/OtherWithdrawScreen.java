package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.TransactionServiceImpl;
import com.mitrais.atm.service.DataValidationServiceImpl;

import java.util.List;
import java.util.Scanner;

public class OtherWithdrawScreen implements Screen{
    Account account = null;
    List<Account> accounts = null;
    TransactionServiceImpl transactionService = new TransactionServiceImpl();

    @Override
    public void showScreen() {
        DataValidationServiceImpl validate = new DataValidationServiceImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw : ");
        String amount = scanner.nextLine();

//        //boolean inputValid = validate.checkWithdrawAmount(amount);
//        if (inputValid) {
//            transactionService.processWithdraw(Integer.valueOf(amount));
//        } else {
//            TransactionScreen transactionScreen = new TransactionScreen();
//            transactionScreen.showScreen();
//        }
    }
}
