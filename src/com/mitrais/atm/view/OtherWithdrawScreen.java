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

    public OtherWithdrawScreen(Account account, List<Account> accounts) {
        this.account = account;
        this.accounts = accounts;
    }
    @Override
    public void showScreen() {
        DataValidation validate = new DataValidation();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw : ");
        String choice = scanner.nextLine();

        String checkInput = validate.checkWithdrawAmount(choice);
        if (checkInput == null) {
            transactionService.processWithdraw(Integer.valueOf(choice), account, accounts);
        } else {
            System.out.println(checkInput);
            WithdrawScreen withdrawScreen = new WithdrawScreen(account, accounts);
            withdrawScreen.showScreen();
        }
    }
}
