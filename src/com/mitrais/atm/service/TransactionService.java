package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.view.FundTransferSummaryScreen;
import com.mitrais.atm.view.SummaryScreen;
import com.mitrais.atm.view.WithdrawScreen;

public class TransactionService {
    AccountService accountService = new AccountServiceImpl();

    private static final String WITHDRAW = "WITHDRAW";
    private static final String FUND_TRANSFER = "FUND TRANSFER";

    Transaction transaction = new Transaction();

    public void processWithdraw(int deduction)  {
        Account loggedAccount = accountService.getLoggedAccount();
        int balance = loggedAccount.getBalance();
        if(balance >= deduction) {
            transaction.addTransaction(loggedAccount, WITHDRAW, String.valueOf(deduction));
            loggedAccount.setBalance(balance - deduction);
            SummaryScreen summaryScreen = new SummaryScreen(deduction);
            summaryScreen.showScreen();
        }else {
            System.out.println("Insufficient balance $" + deduction);
            WithdrawScreen withdrawScreen = new WithdrawScreen();
            withdrawScreen.showScreen();
        }
    }
    public void processFundTransfer(Account dest, String amount, String refNo) {
        Account origin = accountService.getLoggedAccount();
        transaction.addTransaction(origin,FUND_TRANSFER,amount);
        origin.setBalance(origin.getBalance() - Integer.parseInt(amount));
        dest.setBalance(dest.getBalance() + Integer.parseInt(amount));

        FundTransferSummaryScreen fundTransferSummaryScreen = new FundTransferSummaryScreen(dest, amount, refNo);
        fundTransferSummaryScreen.showScreen();
    }
}
