import com.mitrais.atm.util.DataValidation;

import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DataValidation validation = new DataValidation();

        boolean loggedIn = false;
        String errorMessage = null;
        Map<String,Object> res;

        System.out.println("=====Welcome to ATM Simulation v1=====");
        do {
            System.out.println("=====Please login=====");
            System.out.print("Enter Account Number : ");
            String accNumber = scanner.nextLine();
            System.out.print("Enter PIN : ");
            String accPin = scanner.nextLine();
            res = validation.checkLoginCredential(accNumber, accPin);
            loggedIn = (Boolean) res.get("isLoggedIn");
            if(res.get("errorMessage") != null) {
                errorMessage = res.get("errorMessage").toString();
                System.out.println("Error : "+errorMessage);
            } else {
                System.out.println("login success");
            }
        }
        while (!loggedIn);


    }
}
