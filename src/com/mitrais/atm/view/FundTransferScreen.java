package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.RandomNumberGenerator;

import java.util.List;
import java.util.Scanner;

public class FundTransferScreen implements Screen{
    Account account = null;
    List<Account> accounts = null;
    TransactionService transactionService = new TransactionService();

    public FundTransferScreen(Account account, List<Account> accounts) {
        this.account = account;
        this.accounts = accounts;
    }
    @Override
    public void showScreen() {
        TransactionScreen transactionScreen = new TransactionScreen(account, accounts);
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Fund Transfer Screen=======");
        System.out.print("Please enter destination account and press enter to continue or" +
                "press enter to go back to Transaction: ");
        String choiceDest = scanner.nextLine();

        if (choiceDest.isEmpty()) {
            transactionScreen.showScreen();
        } else {
            System.out.print("Please enter transfer amount and  press enter to continue or" +
                    "  press enter to go back to Transaction: ");
            String choiceAmount = scanner.nextLine();
            if(choiceAmount.isEmpty()) {
                transactionScreen.showScreen();
            }else {
                DataValidation validate = new DataValidation();
                Account destAcc = validate.checkFundInputData(choiceDest, choiceAmount, account, accounts);
                if(destAcc != null) {
                    RandomNumberGenerator random = new RandomNumberGenerator();
                    String refNum = random.getRandom6DigitNumber();
                    System.out.println("Reference Number : "+refNum);
                    System.out.println("Press enter to continue...");
                    Scanner s = new Scanner(System.in);
                    s.nextLine();

                    System.out.println("Destination Account : "+choiceDest);
                    System.out.println("Amount : $"+choiceAmount);
                    System.out.println("Reference Number : "+refNum);

                    System.out.println("1. Confirm Transfer");
                    System.out.println("2. Cancel Transfer");

                    System.out.print("Please choose option[2]: ");
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1" :
                            transactionService.processFundTransfer(account, destAcc,choiceAmount,refNum, accounts);
                        case "2": case "" :
                            transactionScreen.showScreen();
                        default:
                            this.showScreen();
                    }
                }else {
                    transactionScreen.showScreen();
                }
            }
        }
    }
}
