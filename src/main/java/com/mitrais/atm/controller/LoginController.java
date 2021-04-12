package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        String errorMsg = dataValidationService.checkLoginCredential(accNumber, accPin);
        if (errorMsg == null) {
            Account account = accountService.getAccount(accNumber, accPin);
            accountService.setLoggedAccount(account);
            model.addAttribute("account", account);
            return "screen/transactionScreen";
        } else {
            model.addAttribute("errorMessage", errorMsg);
            return "index";
        }
    }
}
