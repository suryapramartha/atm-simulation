package com.mitrais.atm.view;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.RandomNumberGenerator;
import com.mitrais.atm.util.UserData;

import java.util.Map;
import java.util.Scanner;

public class FundTransferScreen implements Screen{
    TransactionService transactionService = new TransactionService();

    @Override
    public void showScreen() {
        TransactionScreen transactionScreen = new TransactionScreen();
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
                Map<String,Object> data = validate.checkFundInputData(choiceDest, choiceAmount);
                if(data.get("error") == null) {
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
                    if(choice.equalsIgnoreCase("1")) {
                        transactionService.processFundTransfer(UserData.loggedAccount, (Account) data.get("destinationAcc"),choiceAmount,refNum);
                    }else if(choice.equalsIgnoreCase("2")){
                        transactionScreen.showScreen();
                    }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                            && !choice.isEmpty()) {
                        this.showScreen();
                    }else {
                        transactionScreen.showScreen();
                    }

                }else {
                    System.out.println(data.get("error").toString());
                    transactionScreen.showScreen();
                }
            }
        }
    }
}
