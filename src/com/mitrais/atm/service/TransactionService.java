package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;
import com.mitrais.atm.view.FundTransferSummaryScreen;
import com.mitrais.atm.view.SummaryScreen;
import com.mitrais.atm.view.WithdrawScreen;

public class TransactionService {

    public void processWithdraw(int deduction) {
        int balance = UserData.loggedAccount.getBalance();
        if(balance >= deduction) {
            UserData.loggedAccount.setBalance(balance - deduction);
            SummaryScreen summaryScreen = new SummaryScreen(deduction);
            summaryScreen.showScreen();
        }else {
            System.out.println("Insufficient balance $" + deduction);
            WithdrawScreen withdrawScreen = new WithdrawScreen();
            withdrawScreen.showScreen();
        }
    }
    public void processFundTransfer(Account origin, Account dest, String amount, String refNo) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(amount));
        dest.setBalance(dest.getBalance() + Integer.parseInt(amount));
        FundTransferSummaryScreen fundTransferSummaryScreen = new FundTransferSummaryScreen(dest, amount, refNo);
        fundTransferSummaryScreen.showScreen();
    }
}
