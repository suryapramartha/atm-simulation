package com.mitrais.atm.service;

public interface DataValidationService {
    String checkLoginCredential(String accNumber, String accPin);

    String checkWithdrawAmount(String amount);

    String checkFundInputData(String destAcc, String amount);

}
