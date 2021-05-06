package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionServiceImpl transactionService;


    @PostMapping(value = "/process-withdraw")
    public String processWithdraw(@RequestParam(value = "withdrawAmount") String amount,
                                  @RequestParam(value = "customWithdraw", required = false) String customAmount,
                                  Model model) {
        String amountInput = amount.equals("custom") ? customAmount : amount;
        try {
            Map<String, Object> updatedAccount = transactionService.processWithdraw(amountInput);
            setSuccessModel(model, updatedAccount);
            return "screen/summaryScreen";
        } catch (Exception e) {
            setErrorModel(model, e.getMessage());
            return "screen/withdrawScreen";
        }
    }

    @PostMapping(value = "/process-fund-transfer")
    public String processFundTransfer(@RequestParam(value = "descAcc") String descAcc,
                                      @RequestParam(value = "transferAmount") String amount,
                                      Model model) throws Exception {
        try {
            Map<String , Object> updatedAccount = transactionService.processFundTransfer(descAcc, amount);
            setSuccessModel(model, updatedAccount);
            return "screen/summaryScreen";
        }catch (Exception e) {
            setErrorModel(model, e.getMessage());
            return "screen/fundTransferScreen";
        }
    }

    private void setSuccessModel(Model model, Map<String, Object> updatedAccount) {
        accountService.setLoggedAccount((Account) updatedAccount.get("account"));
        model.addAttribute("account", accountService.getLoggedAccount());
        model.addAttribute("transaction", updatedAccount.get("transaction"));
    }

    private void setErrorModel(Model model, String errorMsg) {
        model.addAttribute("account", accountService.getLoggedAccount());
        model.addAttribute("errorMessage", errorMsg);
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

}
