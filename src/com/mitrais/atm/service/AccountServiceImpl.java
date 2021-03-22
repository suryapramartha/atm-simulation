package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.util.AccountRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService{
    AccountRepository accountRepository = new AccountRepository();

    private static final String CSV_SEPARATOR = ",";
    public AccountServiceImpl() { }

    @Override
    public List<Account> loadAccountsFromCSV(String path) throws Exception {
        List<List<String>> loadCSV = loadCSVFile(path);
        List<Account> result = loadCSV.stream()
                .map(mapToAccount)
                .collect(Collectors.toList());

        accountRepository.setAccounts(filterDuplicateAccount(result));
        return accountRepository.getAccounts();
    }

    @Override
    public Account getAccount(String accNumber, String accPin) {
        List<Account> accounts = accountRepository.getAccounts();
        Predicate<Account> filterAccount = p ->
                p.getAccNumber().equalsIgnoreCase(accNumber) && p.getPin().equalsIgnoreCase(accPin);
        return accounts.stream().filter(filterAccount).findFirst().get();
    }

    @Override
    public Account getLoggedAccount() {
        return accountRepository.getLoggedAccount();
    }

    @Override
    public void setLoggedAccount(Account account) {
        accountRepository.setLoggedAccount(account);
    }

    @Override
    public List<Account> getAccountList() {
        return accountRepository.getAccounts();
    }

    public List<List<String>> loadCSVFile(String filepath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filepath);
            InputStream inputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // change to Files

            data = bufferedReader
                    .lines()
                    .skip(1)
                    .map(p -> Arrays.asList(p.split(CSV_SEPARATOR)))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return data;
    };

    private Function<List<String>, Account> mapToAccount = line -> {
      Account account = new Account();
      account.setName(line.get(0));
      account.setPin(line.get(1));
      account.setBalance(Integer.valueOf(line.get(2)));
      account.setAccNumber(line.get(3));
      return account;
    };

    private List<Account> filterDuplicateAccount(List<Account> data) {
        List<Account> result =  data.stream()
                .distinct()
                .collect(Collectors.toList());
        if (result.size() < data.size())
             System.out.println("Error : Duplicate Account Number on CSV file!");
        return result;
    };

//    public void updateCSVOnTransaction(Account origin, Account dest, String command, String amount) {
//        try {
//            File oldFile = new File(FILE_INPUT_PATH);
//            File newFile = new File(FILE_TEMP_PATH);
//            List<List<String>> inputData = loadCSVFile(FILE_INPUT_PATH);
//            FileWriter csvWriter = generateCSVTemplate();
//            if (command.equals(WITHDRAW)) {
//                inputData.stream().forEach(p -> {
//                    try {
//                        if (p.get(3).equalsIgnoreCase(origin.getAccNumber())) {
//                            p.set(2, String.valueOf(origin.getBalance()));
//                        }
//                        csvWriter.append(String.join(CSV_SEPARATOR, p));
//                        csvWriter.append("\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//            if (command.equals(FUND_TRANSFER)) {
//                inputData.stream().forEach(p -> {
//                    try {
//                        if (p.get(3).equalsIgnoreCase(origin.getAccNumber()))
//                            p.set(2, String.valueOf(origin.getBalance()));
//                        if (p.get(3).equalsIgnoreCase(dest.getAccNumber()))
//                            p.set(2, String.valueOf(dest.getBalance()));
//                        csvWriter.append(String.join(CSV_SEPARATOR, p));
//                        csvWriter.append("\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//            csvWriter.flush();
//            csvWriter.close();
//            oldFile.delete();
//            newFile.renameTo(oldFile);
//
//            saveToHistory(origin, dest, command, amount);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public List<List<String>> getTransactionHistory(String accountNumber)  {
//        List<List<String>> inputData = null;
//        try {
//            inputData = loadCSVFile(FILE_HISTORY_PATH);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return inputData.stream()
//                .filter(c -> c.get(0).equalsIgnoreCase(accountNumber))
//                .limit(10)
//                .collect(Collectors.toList());
//    }

//    private void saveToHistory(Account origin, Account dest, String command, String amount) throws IOException {
//        File oldFile = new File(FILE_HISTORY_PATH);
//        File newFile = new File(FILE_TEMP_PATH);
//        List<List<String>> inputData = loadCSVFile(FILE_HISTORY_PATH);
//        FileWriter csvWriter = generateCSVHistoryTemplate();
//        inputData.stream().forEach(p -> {
//            try {
//                csvWriter.append(String.join(CSV_SEPARATOR, p));
//                csvWriter.append("\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        List<String> newInput = new ArrayList<>();
//        newInput.add(0, origin.getAccNumber());
//        newInput.add(1, String.valueOf(new Date()));
//        newInput.add(2, command);
//        newInput.add(3,amount);
//        newInput.add(4, String.valueOf(origin.getBalance()));
//        csvWriter.append(String.join(CSV_SEPARATOR, newInput));
//        csvWriter.append("\n");
//        if (command.equals(FUND_TRANSFER)) {
//            List<String> newInputReceiver = new ArrayList<>();
//            newInputReceiver.add(0, dest.getAccNumber());
//            newInputReceiver.add(1, String.valueOf(new Date()));
//            newInputReceiver.add(2, FUND_TRANSFER_RECEIVED);
//            newInputReceiver.add(3,amount);
//            newInputReceiver.add(4, String.valueOf(dest.getBalance()));
//            csvWriter.append(String.join(CSV_SEPARATOR, newInputReceiver));
//            csvWriter.append("\n");
//        }
//        csvWriter.flush();
//        csvWriter.close();
//        oldFile.delete();
//        newFile.renameTo(oldFile);
//    }

//    private FileWriter generateCSVTemplate() throws IOException {
//        FileWriter csvWriter = new FileWriter(FILE_TEMP_PATH);
//        csvWriter.append("Name");
//        csvWriter.append(",");
//        csvWriter.append("PIN");
//        csvWriter.append(",");
//        csvWriter.append("Balance");
//        csvWriter.append(",");
//        csvWriter.append("Account Number");
//        csvWriter.append("\n");
//
//        return csvWriter;
//    }
//
//    private FileWriter generateCSVHistoryTemplate() throws IOException {
//        FileWriter csvWriter = new FileWriter(FILE_TEMP_PATH);
//        csvWriter.append("Account Number");
//        csvWriter.append(",");
//        csvWriter.append("Date");
//        csvWriter.append(",");
//        csvWriter.append("Transaction Type");
//        csvWriter.append(",");
//        csvWriter.append("Amount");
//        csvWriter.append(",");
//        csvWriter.append("Balance");
//        csvWriter.append("\n");
//
//        return csvWriter;
//    }

}
