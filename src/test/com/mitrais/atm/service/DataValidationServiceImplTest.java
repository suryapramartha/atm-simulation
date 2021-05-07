package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataValidationServiceImplTest {

    @InjectMocks
    private DataValidationServiceImpl dataValidationService;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private static Account origin;
    private static Account loggedAccount;
    private static List<Account> accountList ;
    private static Account dest;

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
        loggedAccount.setAccNumber("121212");
        loggedAccount.setPin("111111");
        loggedAccount.setName("loggedAccount");
        loggedAccount.setBalance(100);

        dest = new Account();
        dest.setAccNumber("999999");
        dest.setBalance(100);
        dest.setName("test");
        dest.setPin("111111");

    }


    @Test
    public void checkAccountNumberCredentialErrorNumberOnlyAccNumber() {
        String accNumber = "11223f";
        String pin = "012108";

        boolean isError = dataValidationService.checkAccountNumberCredential(accNumber);
        assertEquals(isError, false);
    }

    @Test
    public void checkLoginCredentialErrorLengthPIN() {
        String accNumber = "112233";
        String pin = "0121084";

        boolean isEror = dataValidationService.checkPIN(pin);
        assertEquals(false, isEror);
    }

    @Test
    public void givenValidInputWhenCheckLoginCredentialThenReturnNoError() throws Exception {
        when(accountRepository.findAll()).thenReturn(accountList);
        String errorMessage = dataValidationService.checkLoginCredential(loggedAccount.getAccNumber(), loggedAccount.getPin());
        assertEquals(errorMessage, null);
    }

    @Test(expected = Exception.class)
    public void givenInvalidAmountWhenCheckWithdrawAmountThenReturnError() throws Exception {

        String error = "Invalid amount";
        // to check numbers input only
        String amount1 = "efbefb";
        // to check if amount is able to divided by 10
        String amount2 = "78";
        String result1 = dataValidationService.checkWithdrawAmount(amount1);
        String result2 = dataValidationService.checkWithdrawAmount(amount2);
    }

    @Test(expected = Exception.class)
    public void givenInvalidInputWhenCheckFundInputDataReturnException() throws Exception {
        when(accountService.getLoggedAccount()).thenReturn(loggedAccount);

        //test account destination number only
        String dest1 = "1664bgd";
        String amount1 = "20";

        String result = dataValidationService.checkFundInputData(dest1, amount1);
    }
    @Test
    public void givenValidInputWhenCheckFundInputDataReturnNoError() throws Exception {
        when(accountService.getLoggedAccount()).thenReturn(loggedAccount);
        when(accountRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(dest));
        //test account destination number only
        String dest1 = "999999";
        String amount1 = "20";

        String errorMsg = dataValidationService.checkFundInputData(dest1, amount1);
        assertEquals(errorMsg, null);
    }
}
