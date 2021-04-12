package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataValidationService;
import com.mitrais.atm.service.TransactionServiceImpl;
import com.mitrais.atm.util.RandomNumberGenerator;
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
    private RandomNumberGenerator randomNumberGenerator;

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

    @PostMapping(value = "/process-fund-transfer")
    public String processFundTransfer(@RequestParam(value = "descAcc") String descAcc,
                                      @RequestParam(value = "transferAmount") String amount,
                                      Model model) throws Exception {
        String errorMessage = dataValidationService.checkFundInputData(descAcc, amount);
        if (!(errorMessage == null)) {
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("errorMessage", errorMessage);
            return "screen/fundTransferScreen";
        }else {
            Account account = accountService.getLoggedAccount();
            Account destAccount = accountService.getAccountByAccNumber(descAcc);
            String refNo = randomNumberGenerator.getRandom6DigitNumber();
            Transaction newFundTransferTrans = new Transaction(account.getAccNumber(), descAcc, FUND_TRANSFER, LocalDate.now(), amount, account.getBalance(), refNo);
            Account updatedAccount = transactionService.processFundTransfer(newFundTransferTrans, account, destAccount);
            accountService.setLoggedAccount(updatedAccount);
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("transaction", newFundTransferTrans);
            return "screen/summaryScreen";
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
