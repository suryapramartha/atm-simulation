package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class DataValidationServiceImplTest {

    @InjectMocks
    private DataValidationService dataValidationService;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private static Account origin;
    private static Account loggedAccount;
    private static List<Account> accountList ;
    private static Account dest;

    String errorMessage;
    boolean isLoggedIn;

    @Before
    public void setUp() throws Exception {
        origin = new Account();
        origin.setAccNumber("121212");
        origin.setBalance(100);
        origin.setName("test");
        origin.setPin("111111");

        accountList = IntStream
                .range(0, 10)
                .mapToObj(i -> origin)
                .collect(Collectors.toList());

        loggedAccount = new Account();
        loggedAccount.setAccNumber("123456");
        loggedAccount.setPin("654321");
        loggedAccount.setName("loggedAccount");
        loggedAccount.setBalance(100);
    }


    @Test
    public void checkAccountNumberCredentialErrorNumberOnlyAccNumber() {
        DataValidationServiceImpl datavalidation = new DataValidationServiceImpl();
        String accNumber = "11223f";
        String pin = "012108";

        boolean isError = datavalidation.checkAccountNumberCredential(accNumber);
        assertEquals(false, isError);
    }

    @Test
    public void checkLoginCredentialErrorLengthPIN() {
        DataValidationServiceImpl datavalidation = new DataValidationServiceImpl();
        String accNumber = "112233";
        String pin = "0121084";

        boolean isEror = datavalidation.checkPIN(pin);
        assertEquals(false, isEror);
    }

//    @Test
//    public void checkWithdrawAmountDidSuccess() throws Exception {
//        when(accountService.getLoggedAccount()).thenReturn(loggedAccount);
//        String amount = "50";
//        errorMessage = dataValidationService.checkWithdrawAmount(amount);
//        assertEquals(errorMessage, null);
//    }

//    @Test
//    public void checkWithdrawAmountInvalidAmount() {
//        DataValidationServiceImpl validation = new DataValidationServiceImpl();
//        String error = "Invalid amount";
//        // to check numbers input only
//        String amount1 = "efbefb";
//        // to check if amount is able to divided by 10
//        String amount2 = "78";
//        Boolean result1 = validation.checkWithdrawAmount(amount1);
//        assertEquals(result1, error);
//        boolean result2 = validation.checkWithdrawAmount(amount2);
//        assertEquals(result2,error);
//    }
//    @Test
//    public void checkWithdrawAmountMaximumAmount() {
//        DataValidationServiceImpl validation = new DataValidationServiceImpl();
//        String amount = "10000000";
//        boolean result = validation.checkWithdrawAmount(amount);
//        assertEquals(result, "Maximum amount to withdraw is $1000");
//    }

//    @Test
//        public void checkFundInputDataInvalid() {
//        DataValidation validation = new DataValidation();
//
//        //test account destination number only
//        String dest1 = "1664bgd";
//        String amount1 = "20";
//
//        Account acc = new Account("John Doe", "012108", 100, "112233");
//
//        result = validation.checkFundInputData(dest1, amount1, acc, accounts);
//        assertEquals(result, null);
//    }
}
