package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.util.DataValidation;

import java.util.List;
import java.util.Scanner;

public class OtherWithdrawScreen implements Screen{
    Account account = null;
    List<Account> accounts = null;
    TransactionService transactionService = new TransactionService();

    @Override
    public void showScreen() {
        DataValidation validate = new DataValidation();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw : ");
        String amount = scanner.nextLine();

        boolean inputValid = validate.checkWithdrawAmount(amount);
        if (inputValid) {
            transactionService.processWithdraw(Integer.valueOf(amount));
        } else {
            TransactionScreen transactionScreen = new TransactionScreen();
            transactionScreen.showScreen();
        }
    }
}
