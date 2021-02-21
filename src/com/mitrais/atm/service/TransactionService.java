package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;
import com.mitrais.atm.view.FundTransferSummaryScreen;
import com.mitrais.atm.view.SummaryScreen;
import com.mitrais.atm.view.WithdrawScreen;

import java.util.List;

public class TransactionService {

    UserData userData = new UserData();

    public void processWithdraw(int deduction, Account loggedAccount, List<Account> accounts)  {
        int balance = loggedAccount.getBalance();
        if(balance >= deduction) {
            loggedAccount.setBalance(balance - deduction);
            userData.updateCSVonWithdraw(loggedAccount);
            SummaryScreen summaryScreen = new SummaryScreen(deduction, loggedAccount,accounts);
            summaryScreen.showScreen();
        }else {
            System.out.println("Insufficient balance $" + deduction);
            WithdrawScreen withdrawScreen = new WithdrawScreen(loggedAccount, accounts);
            withdrawScreen.showScreen();
        }
    }
    public void processFundTransfer(Account origin, Account dest, String amount, String refNo, List<Account> accounts) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(amount));
        dest.setBalance(dest.getBalance() + Integer.parseInt(amount));
        FundTransferSummaryScreen fundTransferSummaryScreen = new FundTransferSummaryScreen(origin,dest, amount, refNo, accounts);
        fundTransferSummaryScreen.showScreen();
    }
}
