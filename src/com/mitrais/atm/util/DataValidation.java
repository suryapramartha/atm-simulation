package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataValidation {


    public DataValidation() {}

    public Map<String,Object> checkLoginCredential(String accNumber, String accPin) {
        Map<String,Object> result = new HashMap<>();
        UserData userData = new UserData();
        List<Account> accData = userData.getUserData();

        String errorMessage = null;
        boolean isLoggedIn = false;
        Account accountData = null;

        //start validate
        if(accNumber.length() != 6){
            errorMessage = "Account Number should have 6 digits length";
        }
        else if(!accNumber.matches("[0-9]+")){
            errorMessage = "Account Number should only contains numbers";
        }
        else if(accPin.length() != 6){
            errorMessage = "PIN should have 6 digits length";
        }
        else if(!accPin.matches("[0-9]+")){
            errorMessage = "PIN should only contains numbers";
        }else {
            for(int i=0; i< accData.size(); i++){
                if(accNumber.equalsIgnoreCase(accData.get(i).getAccNumber()) &&
                        accPin.equalsIgnoreCase(accData.get(i).getPin())){
                    accountData = accData.get(i);
                    isLoggedIn = true;
                    break;
                } else {
                    errorMessage = "Invalid Account Number/PIN";
                    result.put("accountData", accData.get(i));
                }
            }
        }
        result.put("isLoggedIn", isLoggedIn);
        result.put("errorMessage", errorMessage);
        result.put("accountData", accountData);
        return result;
    }

    public String checkWithdrawAmount(String amount) {
        String errorMessage = null;

        if(!amount.matches("[0-9]+")){
            errorMessage = "Invalid amount";
        }else {
            int amountNumb = Integer.valueOf(amount);
            if(amountNumb > 1000) {
                errorMessage = "Maximum amount to withdraw is $1000";
            }else if (amountNumb%10 != 0) {
                errorMessage = "Invalid amount";
            }
        }
        return errorMessage;
    }
}
