import com.mitrais.atm.view.WelcomeScreen;

public class Main {

    public static void main(String[] args) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        do {
            welcomeScreen.showScreen();
        } while (true);
    }
}
