import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;
import com.mitrais.atm.view.WelcomeScreen;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserData userData = new UserData();
        List<Account> accounts =  userData.getUserData();

        do {
            WelcomeScreen welcomeScreen = new WelcomeScreen(accounts);
            welcomeScreen.showScreen();
        } while (true);
    }
}
