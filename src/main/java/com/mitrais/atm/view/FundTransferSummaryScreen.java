package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.AccountServiceImpl;

import java.util.List;
import java.util.Scanner;

public class FundTransferSummaryScreen implements Screen{

    AccountService accountService = new AccountServiceImpl();
    Account destAcc;
    String amount;
    String refNo;
    List<Account> accounts = null;

    public FundTransferSummaryScreen(Account destAcc, String amount, String refNo ) {
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
        System.out.println("Balance : $"+ accountService.getLoggedAccount().getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": case "2": case "":
                TransactionScreen transactionScreen = new TransactionScreen();
                transactionScreen.showScreen();
            default:
                this.showScreen();
        }
    }
}
