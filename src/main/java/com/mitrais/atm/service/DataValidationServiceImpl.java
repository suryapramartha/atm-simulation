package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class DataValidationServiceImpl implements DataValidationService {
    AccountService accountService = new AccountServiceImpl();

    @Autowired
    private AccountRepository accountRepository;

    public DataValidationServiceImpl() {}

    public boolean checkAccountNumberCredential(String accNumber) {
        boolean isValid = true;
        if(accNumber.length() != 6){
            System.out.println("Account Number should have 6 digits length");
            isValid = false;
        }
        else if(!accNumber.matches("[0-9]+")){
            System.out.println("Account Number should only contains numbers");
            isValid = false;
        }
        return isValid;
    }
    public boolean checkPIN(String accPin) {
        boolean isValid = true;
        if(accPin.length() != 6){
            System.out.println("PIN should have 6 digits length");
            isValid = false;
        }
        else if(!accPin.matches("[0-9]+")){
            System.out.println("PIN should only contains numbers");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String checkLoginCredential(String accNumber, String accPin) {
        String error = null;
        List<Account> accounts = accountRepository.findAll();
        Predicate<Account> filterAccount = p ->
                p.getAccNumber().equalsIgnoreCase(accNumber) && p.getPin().equalsIgnoreCase(accPin);
        Optional<Account> result = accounts.stream().filter(filterAccount).findFirst();

        if(!result.isPresent()) {
            error = "Invalid Account Number/PIN";
        }
        return error;
    }

    @Override
    public String checkWithdrawAmount(String amount) throws Exception {
        String error = null;
        Account account = accountService.getLoggedAccount();

        if(!amount.matches("[0-9]+")){
            error = "Invalid amount";
        }else {
            int amountNumb = Integer.valueOf(amount);
            if(amountNumb > 1000) {
                error = "Maximum amount to withdraw is $1000";
            }else if (amountNumb%10 != 0) {
                error = "Invalid amount";
            }else if (amountNumb > account.getBalance()) {
                error = "Insufficient Balance $"+amountNumb;
            }
        }
        if(error != null) throw new Exception(error);
        return null;
    }

    @Override
    public String checkFundInputData(String dest, String amount) throws Exception {
        String errorMsg = null;
        int balance = accountService.getLoggedAccount().getBalance();

        Optional<Account> existingAcc = accountRepository.findById(dest);
        if (!existingAcc.isPresent()) {
            errorMsg = "Invalid destination account";
        }
        if(!dest.matches("[0-9]+")) {
            errorMsg = "Invalid destination account";
        } else {
             if(!amount.matches("[0-9]+")) {
                errorMsg = "Invalid amount";
            }else {
                if(amount.length() > 10) {
                    amount = amount.substring(1,10);
                }
                int amountNumb = Integer.valueOf(amount);
                if(amountNumb > 1000) {
                    errorMsg = "Maximum amount to withdraw is $1000";
                }else if (amountNumb<1) {
                    errorMsg = "Minimum amount to withdraw is $1";
                }
                else if(amountNumb > balance) {
                    errorMsg = "Insufficient balance $"+amountNumb;
                }
            }
        }
        if(errorMsg != null) throw new Exception(errorMsg);
        return null;
    }
}
