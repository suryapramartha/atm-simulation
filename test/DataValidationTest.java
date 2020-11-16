import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.UserData;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DataValidationTest {

    String errorMessage;
    boolean isLoggedIn;
    Account accountData;

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
}
