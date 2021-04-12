//package com.mitrais.atm.view;
//
//import com.mitrais.atm.service.AccountService;
//
//import java.util.Scanner;
//
//public class TransactionScreen implements Screen{
//
//    AccountService accountService;
//
//    @Override
//    public void showScreen() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("=====Transaction Screen=====");
//        System.out.println("1. Withdraw");
//        System.out.println("2. Fund Transfer");
//        System.out.println("3. Transaction History");
//        System.out.println("4. Exit");
//        System.out.print("Please choose option[4]: ");
//        String choice = scanner.nextLine();
//
//        switch (choice) {
//            case "1":
//                WithdrawScreen withdrawScreen = new WithdrawScreen();
//                withdrawScreen.showScreen();
//            case "2":
//                FundTransferScreen fundTransferScreen = new FundTransferScreen();
//                fundTransferScreen.showScreen();
//            case "3":
//                TransactionHistoryScreen transactionHistoryScreen = new TransactionHistoryScreen();
//                transactionHistoryScreen.showScreen();
//            case "4": case "":
//                WelcomeScreen welcomeScreen = new WelcomeScreen();
//                welcomeScreen.showScreen();
//            default:
//                this.showScreen();
//        }
//    }
//}
