import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.UserData;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataValidationTest {

    String ERROR_INVALID_ACCOUNT = "Invalid account";
    String ERROR_INVALID_AMOUNT = "Invalid amount";
    String ERROR_MAX_WITHDRAW_AMOUNT = "Maximum amount to withdraw is $1000";
    String ERROR_MIN_WITHDRAW_AMOUNT = "Minimum amount to withdraw is $1";
    String errorMessage;
    boolean isLoggedIn;
    Account accountData;
    List<Account> accounts;

    Map<String,Object> result;
    UserData userData;

    @Before
    public void setUp() {
        errorMessage = null;
        isLoggedIn = false;
        accountData = null;

        result = new HashMap<>();
    }

    @Test
    public void checkLoginCredentialDidSuccess() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "112233";
        String pin = "012108";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(true, result.get("isLoggedIn"));
        assertEquals(null, result.get("errorMessage"));
    }

    @Test
    public void checkLoginCredentialErrorLengthAccNumber() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "112233f";
        String pin = "012108";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(false, result.get("isLoggedIn"));
        assertEquals("Account Number should have 6 digits length", result.get("errorMessage"));
    }

    @Test
    public void checkLoginCredentialErrorNumberOnlyAccNumber() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "11223f";
        String pin = "012108";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(false, result.get("isLoggedIn"));
        assertEquals("Account Number should only contains numbers", result.get("errorMessage"));
    }

    @Test
    public void checkLoginCredentialErrorLengthPIN() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "112233";
        String pin = "0121084";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(false, result.get("isLoggedIn"));
        assertEquals("PIN should have 6 digits length", result.get("errorMessage"));
    }

    @Test
    public void checkLoginCredentialErrorNumberOnlyPIN() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "112233";
        String pin = "01210b";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(false, result.get("isLoggedIn"));
        assertEquals("PIN should only contains numbers", result.get("errorMessage"));
    }

    @Test
    public void checkLoginCredentialErrorAccOrPIN() {
        DataValidation datavalidation = new DataValidation();
        String accNumber = "112232";
        String pin = "012101";

        result = datavalidation.checkLoginCredential(accNumber, pin);
        assertEquals(false, result.get("isLoggedIn"));
        assertEquals("Invalid Account Number/PIN", result.get("errorMessage"));
    }

    @Test
    public void checkWithdrawAmountDidSuccess() {
        DataValidation validation = new DataValidation();
        String amount = "900";
        String result = validation.checkWithdrawAmount(amount);
        assertEquals(result, null);
    }

    @Test
    public void checkWithdrawAmountInvalidAmount() {
        DataValidation validation = new DataValidation();
        String error = "Invalid amount";
        // to check numbers input only
        String amount1 = "efbefb";
        // to check if amount is able to divided by 10
        String amount2 = "78";
        String result1 = validation.checkWithdrawAmount(amount1);
        assertEquals(result1, error);
        String result2 = validation.checkWithdrawAmount(amount2);
        assertEquals(result2,error);
    }
    @Test
    public void checkWithdrawAmountMaximumAmount() {
        DataValidation validation = new DataValidation();
        String amount = "10000000";
        String result = validation.checkWithdrawAmount(amount);
        assertEquals(result, "Maximum amount to withdraw is $1000");
    }

    @Test
    public void checkFundInputDataIsValid() {
        DataValidation validation = new DataValidation();
        String dest = "112244";
        String amount = "20";
        Account acc = new Account("John Doe", "012108", 100, "112233");

        result = validation.checkFundInputData(dest, amount, acc);

        assertEquals(result.get("error"), null);
    }

    @Test
        public void checkFundInputDataInvalid() {
        DataValidation validation = new DataValidation();
        //test account destination number only
        String dest1 = "1664bgd";
        String amount1 = "20";

        //test max amount to transfer
        String dest2 = "112244";
        String amount2 = "2000000000";

        //test min amount to transfer
        String dest3 = "112244";
        String amount3 = "0";

        //test amount number only
        String dest4 = "112244";
        String amount4 = "yegf";

        //test insufficient balance
        String dest5 = "112244";
        String amount5 = "1000";

        Account acc = new Account("John Doe", "012108", 100, "112233");

        result = validation.checkFundInputData(dest1, amount1, acc);
        assertEquals(result.get("error").toString(), ERROR_INVALID_ACCOUNT);

        result = validation.checkFundInputData(dest2, amount2, acc);
        assertEquals(result.get("error").toString(), ERROR_MAX_WITHDRAW_AMOUNT);

        result = validation.checkFundInputData(dest3, amount3, acc);
        assertEquals(result.get("error").toString(), ERROR_MIN_WITHDRAW_AMOUNT);

        result = validation.checkFundInputData(dest4, amount4, acc);
        assertEquals(result.get("error").toString(), ERROR_INVALID_AMOUNT);

        result = validation.checkFundInputData(dest5, amount5, acc);
        assertEquals(result.get("error").toString(),"Insufficient balance $"+amount5);
    }
}
