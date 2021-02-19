import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;
import com.mitrais.atm.view.WelcomeScreen;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        UserData userData = new UserData();
        List<Account> accounts = userData.getUserDataFromCSV();
        if(accounts != null)
            Stream.of(accounts).forEach(System.out::println);
            do {
                WelcomeScreen welcomeScreen = new WelcomeScreen(accounts);
                welcomeScreen.showScreen();
            } while (true);
    }
}
