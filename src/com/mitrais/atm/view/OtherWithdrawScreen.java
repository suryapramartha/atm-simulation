package com.mitrais.atm.view;

import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.util.DataValidation;

import java.util.Scanner;

public class OtherWithdrawScreen implements Screen{
    TransactionService transactionService = new TransactionService();

    @Override
    public void showScreen() {
        DataValidation validate = new DataValidation();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw : ");
        String choice = scanner.nextLine();

        String checkInput = validate.checkWithdrawAmount(choice);
        if (checkInput == null) {
            transactionService.processWithdraw(Integer.valueOf(choice));
        } else {
            System.out.println(checkInput);
            WithdrawScreen withdrawScreen = new WithdrawScreen();
            withdrawScreen.showScreen();
        }
    }
}
