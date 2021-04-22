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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
             dataValidationService.checkWithdrawAmount(amountInput);
             return processWithdraw(amountInput, model);
        } catch (Exception e) {
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("errorMessage", e.getMessage());
            return "screen/withdrawScreen";
        }
    }

    @PostMapping(value = "/process-fund-transfer")
    public String processFundTransfer(@RequestParam(value = "descAcc") String descAcc,
                                      @RequestParam(value = "transferAmount") String amount,
                                      Model model) throws Exception {
        try {
            dataValidationService.checkFundInputData(descAcc, amount);

            Account account = accountService.getLoggedAccount();
            Account destAccount = accountService.getAccountByAccNumber(descAcc);
            String refNo = randomNumberGenerator.getRandom6DigitNumber();
            Transaction newFundTransferTrans = new Transaction(account.getAccNumber(), descAcc, FUND_TRANSFER, LocalDate.now(), amount, account.getBalance(), refNo);
            Account updatedAccount = transactionService.processFundTransfer(newFundTransferTrans, account, destAccount);
            accountService.setLoggedAccount(updatedAccount);
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("transaction", newFundTransferTrans);
            return "screen/summaryScreen";
        }catch (Exception e) {
            model.addAttribute("account", accountService.getLoggedAccount());
            model.addAttribute("errorMessage", e.getMessage());
            return "screen/fundTransferScreen";
        }
    }
    @PostMapping(value = "/filterByDate")
    public String filterByDate(@RequestParam(value = "dateFilter", required = false) String date,
                               @RequestParam(value = "limitFilter", required = false) int limit,
                                      Model model) throws Exception {
            List<Transaction> transactionList;
            Map<String, String> input = new HashMap<>();
            input.put("limit", String.valueOf(limit));
            Account account = accountService.getLoggedAccount();
            if (date.isEmpty()) {
                input.put("date", "ALL");
                transactionList = transactionService.getTransactionHistory(account.getAccNumber(), limit);
            }else {
                LocalDate query = LocalDate.parse(date);
                input.put("date", query.toString());
                transactionList = transactionService.getTransactionHistoryOnDate(account.getAccNumber(),query, limit);
            }
            model.addAttribute("filterInput", input);
            model.addAttribute("account", account);
            model.addAttribute("transactionList", transactionList);
            return "screen/transactionHistoryScreen";
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
