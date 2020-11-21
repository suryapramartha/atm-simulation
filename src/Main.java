import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.DataValidation;
import com.mitrais.atm.util.RandomNumberGenerator;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        welcomeScreen(false);
    }

    private static void welcomeScreen(boolean loggedIn) {
        Scanner scanner = new Scanner(System.in);
        DataValidation validation = new DataValidation();

        String errorMessage = null;

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
            fundTransferScreen(account);
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
            otherWithdrawScreen(account);
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

    private static void processWithdraw(Account account, int deduction) {
        boolean isSufficient = true;
        int balance = account.getBalance();
        if(balance >= deduction) {
            account.setBalance(balance - deduction);
        }else {
            isSufficient = false;
        }
        if (isSufficient) {
            summaryScreen(account, deduction);
        } else {
            System.out.println("Insufficient balance $" + account.getBalance());
            withdrawScreen(account);
        }
    }

    private static void processFundTransfer(Account origin, Account dest, String amount, String refNo) {
        origin.setBalance(origin.getBalance() - Integer.parseInt(amount));
        dest.setBalance(dest.getBalance() + Integer.parseInt(amount));

        fundTransferSummary(origin, dest, amount, refNo);
    }

    private static void fundTransferSummary(Account origin, Account dest, String amount, String refNo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Fund Transfer Summary=======");
        System.out.println("Destination Account : "+ dest.getAccNumber());
        System.out.println("Transfer Amount : $"+ amount);
        System.out.println("Reference Number : "+ refNo);
        System.out.println("Balance : $"+ origin.getBalance());
        System.out.println("\n");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        System.out.print("Please choose option[2]: ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("1")) {
            transactionScreen(origin);
        }else if(choice.equalsIgnoreCase("2")){
            welcomeScreen(false);
        }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                && !choice.isEmpty()) {
            fundTransferSummary(origin, dest, amount, refNo);
        }else {
            welcomeScreen(false);
        }
    }

    public static void fundTransferScreen(Account acc) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Fund Transfer Screen=======");

        System.out.print("Please enter destination account and press enter to continue or" +
                "press enter to go back to Transaction: ");
        String choiceDest = scanner.nextLine();
        if (choiceDest.isEmpty()) {
            transactionScreen(acc);
        } else {
            System.out.print("Please enter transfer amount and  press enter to continue or" +
                    "  press enter to go back to Transaction: ");
            String choiceAmount = scanner.nextLine();
            if(choiceAmount.isEmpty()) {
                transactionScreen(acc);
            }else {
                DataValidation validate = new DataValidation();
                Map<String,Object> data = validate.checkFundInputData(choiceDest, choiceAmount, acc);
                if(data.get("error") == null) {
                    RandomNumberGenerator random = new RandomNumberGenerator();
                    String refNum = random.getRandom6DigitNumber();
                    System.out.println("Reference Number : "+refNum);
                    System.out.println("Press enter to continue...");
                    Scanner s = new Scanner(System.in);
                    s.nextLine();

                    System.out.println("Destination Account : "+choiceDest);
                    System.out.println("Amount : $"+choiceAmount);
                    System.out.println("Reference Number : "+refNum);

                    System.out.println("1. Confirm Transfer");
                    System.out.println("2. Cancel Transfer");

                    System.out.print("Please choose option[2]: ");
                    String choice = scanner.nextLine();
                    if(choice.equalsIgnoreCase("1")) {
                        processFundTransfer(acc, (Account) data.get("destinationAcc"),choiceAmount,refNum);
                    }else if(choice.equalsIgnoreCase("2")){
                        transactionScreen(acc);
                    }else if(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2"))
                            && !choice.isEmpty()) {
                        fundTransferScreen(acc);
                    }else {
                        transactionScreen(acc);
                    }

                }else {
                    System.out.println(data.get("error").toString());
                    transactionScreen(acc);
                }
            }
        }
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

    public static void otherWithdrawScreen(Account account) {
        DataValidation validate = new DataValidation();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw : ");
        String choice = scanner.nextLine();

        String checkInput = validate.checkWithdrawAmount(choice);
        if (checkInput == null) {
            processWithdraw(account, Integer.valueOf(choice));
        } else {
            System.out.println(checkInput);
            withdrawScreen(account);
        }
    }
}
