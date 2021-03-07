import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.UserData;
import com.mitrais.atm.view.WelcomeScreen;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        UserData userData = new UserData();
        List<Account> accounts = userData.getUserDataFromCSV();
        Stream.of(accounts).forEach(System.out::println);
        do {
           WelcomeScreen welcomeScreen = new WelcomeScreen(accounts);
           welcomeScreen.showScreen();
        } while (true);
    }
}
