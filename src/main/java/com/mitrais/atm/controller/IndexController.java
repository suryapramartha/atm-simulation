package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller("/")
public class IndexController {
    @Autowired
    private AccountService accountService;

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
    public String viewFundTransferScreen(){ return "screen/fundTransferScreen";}

    @GetMapping(value = "/histories")
    public String viewTransactionHistoryScreen(){ return "screen/transactionHistoryScreen";}

    @GetMapping(value = "/transaction")
    public String viewTransactionScreen(Model model){
        Account account = accountService.getLoggedAccount();
        model.addAttribute("account", account);
        return "screen/transactionScreen";
    }
}
