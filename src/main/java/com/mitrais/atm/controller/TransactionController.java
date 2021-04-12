package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataValidationService;
import com.mitrais.atm.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DataValidationService dataValidationService;

    @Autowired
    private TransactionServiceImpl transactionService;

    private static final String WITHDRAW = "WITHDRAW";
    private static final String FUND_TRANSFER = "FUND TRANSFER";

    @PostMapping(value = "/process-withdraw")
    public String processWithdraw(@RequestParam(value = "withdrawAmount") String amount,
                                  @RequestParam(value = "customWithdraw", required = false) String customAmount,
                                  Model model) {
        String amountInput = amount.equals("custom") ? customAmount : amount;
        String errorMessage = dataValidationService.checkWithdrawAmount(amountInput);
        if (!(errorMessage == null)) {
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("errorMessage", errorMessage);
            return "screen/withdrawScreen";
        } else {
            return processWithdraw(amountInput, model);
        }
    }

    private String processWithdraw(String amount, Model model) {
        Account account = accountService.getLoggedAccount();
        Transaction newTransaction = new Transaction(account.getAccNumber(), WITHDRAW, LocalDate.now(), amount, account.getBalance());
        Account updatedAccount = transactionService.processWithdraw(newTransaction, account);
        accountService.setLoggedAccount(updatedAccount);
        model.addAttribute("account", accountService.getLoggedAccount());
        model.addAttribute("transaction", newTransaction);
        return "screen/summaryScreen";
    }
}
