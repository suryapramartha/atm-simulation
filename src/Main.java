import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.UserData;

import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserData userData = new UserData();
        DataValidation validation = new DataValidation();

        boolean loggedIn = false;
        List<Account> accData = userData.getUserData();

        System.out.println("=====Welcome to ATM Simulation v1=====");
        do {
            System.out.print("Enter Account Number : ");
            String accNumber = scanner.nextLine();
            System.out.print("Enter PIN : ");
            String accPin = scanner.nextLine();
            System.out.println("Acc number is: " + accNumber + ", PIN : "+accPin);  // Output user input
            loggedIn = validation.checkLoginCredential(accNumber, accPin);

        }
        while (!loggedIn);

    }
}
