package com.mitrais.atm.service;

public interface DataValidationService {
    String checkLoginCredential(String accNumber, String accPin) throws Exception;

    String checkWithdrawAmount(String amount) throws Exception;

    String checkFundInputData(String destAcc, String amount) throws Exception;

}
