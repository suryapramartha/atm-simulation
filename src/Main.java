import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        boolean loggedIn = false;
        welcomeScreen(loggedIn);
    }

    private static void welcomeScreen(boolean loggedIn) {
        Scanner scanner = new Scanner(System.in);

        String errorMessage = null;
        DataValidation validation = new DataValidation();

        Map<String,Object> res;

        System.out.println("======================================");
        System.out.println("=====Welcome to ATM Simulation v1=====");
        do {
            System.out.println("=============Please login=============");
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
                Account account = (Account) res.get("accountData");
                transactionScreen(account);
            }
        }
        while (!loggedIn);
    }

    public static void transactionScreen(Account account) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====Transaction Screen=====");
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.print("Please choose option[3]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            withdrawScreen(account);
        }else if(choice.equalsIgnoreCase("2")){
            fundTransferScreen();
        }else if(choice.equalsIgnoreCase("3")){
            welcomeScreen(false);
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2") ||
        choice.equalsIgnoreCase("3")) && !choice.isEmpty()) {
            transactionScreen(account);
        }else {
            welcomeScreen(false);
        }
    }

    public static void withdrawScreen(Account account) {
        Map<String,Object> result = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("=====Withdraw Screen=====");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            processWithdraw(account, 10);
        }else if(choice.equalsIgnoreCase("2")){
            processWithdraw(account, 50);
        }else if(choice.equalsIgnoreCase("3")){
            processWithdraw(account, 100);
        }else if(choice.equalsIgnoreCase("4")){
            otherWithdrawScreen();
        }
        else if(choice.equalsIgnoreCase("5")){
            transactionScreen(account);
        }
        else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2") ||
                choice.equalsIgnoreCase("3") ||
                choice.equalsIgnoreCase("4") ||
                choice.equalsIgnoreCase("5")) && !choice.isEmpty()) {
            withdrawScreen(account);
        }else {
            transactionScreen(account);
        }
    }

    private static void processWithdraw(Account account, int i) {
        Map<String, Object> result;
        result = account.withdrawFunds(account, i);
        if (result.get("isSufficient").equals(true)) {
            summaryScreen((Account) result.get("account"), i);
        } else {
            System.out.println("Insufficient balance $" + account.getBalance());
            withdrawScreen(account);
        }
    }

    public static void fundTransferScreen() {
        System.out.println(("fund transfer screen"));
    }

    public static void summaryScreen(Account acc, int withdraw) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Summary Screen=======");
        System.out.println(new Date());
        System.out.println("Withdraw : $"+withdraw);
        System.out.println("Balance : $"+acc.getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            transactionScreen(acc);
        }else if(choice.equalsIgnoreCase("2")){
            welcomeScreen(false);
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                && !choice.isEmpty()) {
            summaryScreen(acc, withdraw);
        }else {
            welcomeScreen(false);
        }
    }

    public static void otherWithdrawScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount to withdraw : ");
    }
}
