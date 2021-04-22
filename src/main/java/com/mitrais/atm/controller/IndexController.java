package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller("/")
public class IndexController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String viewMainScreen() {
        return "index";
    }

    @GetMapping(value = "/withdraw")
    public String viewWithdrawScreen(Model model){
        Account account = accountService.getLoggedAccount();
        model.addAttribute("account", account);
        return "screen/withdrawScreen";
    }

    @GetMapping(value = "/fund-transfer")
    public String viewFundTransferScreen(Model model){
        Account account = accountService.getLoggedAccount();
        model.addAttribute("account", account);
        return "screen/fundTransferScreen";}

    @GetMapping(value = "/histories")
    public String viewTransactionHistoryScreen(Model model){
        Account account = accountService.getLoggedAccount();
        List<Transaction> listTransaction = transactionService.getTransactionHistory(account.getAccNumber(), 10);
        model.addAttribute("account", account);
        model.addAttribute("transactionList", listTransaction);
        return "screen/transactionHistoryScreen";}

    @GetMapping(value = "/transaction")
    public String viewTransactionScreen(Model model){
        Account account = accountService.getLoggedAccount();
        model.addAttribute("account", account);
        return "screen/transactionScreen";
    }
}
