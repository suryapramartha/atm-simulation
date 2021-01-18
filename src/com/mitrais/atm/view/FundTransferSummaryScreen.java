package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;

import java.util.List;
import java.util.Scanner;

public class FundTransferSummaryScreen implements Screen{

    Account destAcc = new Account();
    Account account = null;
    String amount;
    String refNo;
    List<Account> accounts = null;

    public FundTransferSummaryScreen(Account sourceAcc, Account destAcc, String amount, String refNo,List<Account> accounts ) {
        this.account = sourceAcc;
        this.destAcc = destAcc;
        this.amount = amount;
        this.refNo = refNo;
        this.accounts = accounts;
    }

    @Override
    public void showScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Fund Transfer Summary=======");
        System.out.println("Destination Account : "+ destAcc.getAccNumber());
        System.out.println("Transfer Amount : $"+ amount);
        System.out.println("Reference Number : "+ refNo);
        System.out.println("Balance : $"+ account.getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            TransactionScreen transactionScreen = new TransactionScreen(account, accounts);
            transactionScreen.showScreen();
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                && !choice.isEmpty()) {
            this.showScreen();
        }
    }
}
