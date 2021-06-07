package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private DataValidationService dataValidationService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(
            @RequestParam(value = "accNumber") String accNumber,
            @RequestParam(value = "accPin") String accPin,
            Model model) {
        try {
            Account account = accountService.getAccount(accNumber, accPin);
            accountService.setLoggedAccount(account);
            model.addAttribute("account", account);
            return "screen/transactionScreen";
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "index";
        }
    }
}
