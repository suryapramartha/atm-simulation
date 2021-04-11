import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.service.AccountServiceImpl;
import com.mitrais.atm.service.DataValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataValidationTest {
    private static final String FILE_INPUT_PATH = "resources/ATM-accounts.csv";

    String ERROR_INVALID_ACCOUNT = "Invalid account";
    String ERROR_INVALID_AMOUNT = "Invalid amount";
    String ERROR_MAX_WITHDRAW_AMOUNT = "Maximum amount to withdraw is $1000";
    String ERROR_MIN_WITHDRAW_AMOUNT = "Minimum amount to withdraw is $1";
    String errorMessage;
    boolean isLoggedIn;
    Account accountData;
    List<Account> accounts;

    Account result;
    AccountServiceImpl accountServiceImpl;

    @Before
    public void setUp() throws Exception {
        errorMessage = null;
        isLoggedIn = false;
        accountData = null;

        result = null;
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
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

    @Test
    public void checkWithdrawAmountDidSuccess() {
        DataValidationServiceImpl validation = new DataValidationServiceImpl();
        String amount = "900";
        boolean result = validation.checkWithdrawAmount(amount);
        assertEquals(result, null);
    }

    @Test
    public void checkWithdrawAmountInvalidAmount() {
        DataValidationServiceImpl validation = new DataValidationServiceImpl();
        String error = "Invalid amount";
        // to check numbers input only
        String amount1 = "efbefb";
        // to check if amount is able to divided by 10
        String amount2 = "78";
        Boolean result1 = validation.checkWithdrawAmount(amount1);
        assertEquals(result1, error);
        boolean result2 = validation.checkWithdrawAmount(amount2);
        assertEquals(result2,error);
    }
    @Test
    public void checkWithdrawAmountMaximumAmount() {
        DataValidationServiceImpl validation = new DataValidationServiceImpl();
        String amount = "10000000";
        boolean result = validation.checkWithdrawAmount(amount);
        assertEquals(result, "Maximum amount to withdraw is $1000");
    }

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
