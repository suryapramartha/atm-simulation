package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserData {

    private static final String FILE_INPUT_PATH = "ATM-accounts.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String FILE_TEMP_PATH = "temp.csv";
    private static final String WITHDRAW = "0";
    private static final String FUND_TRANSFER = "1";
    public UserData() { }

    public List<Account> getUserData() {
        List<Account> userData = new ArrayList<>();
        Account data1 = new Account("John Doe", "012108", 100, "112233");
        Account data2 = new Account("Jane Doe", "932012", 30, "112244");
        userData.add(data1);
        userData.add(data2);
        return userData;
    }

    public List<Account> getUserDataFromCSV() throws IOException {
        List<List<String>> loadCSV = loadCSVFile();
        List<Account> result = loadCSV.stream()
                .map(mapToAccount)
                .collect(Collectors.toList());
        boolean isNotValid = validateCSVData(result);
        if(isNotValid)
            result = null;
        return result;
    }

    public List<List<String>> loadCSVFile() throws IOException {
        List<List<String>> data = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(FILE_INPUT_PATH);
            InputStream inputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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

    private boolean validateCSVData(List<Account> data) {
        boolean isNotValid =  data.stream()
                .map(p -> p.getAccNumber())
                .distinct()
                .count() < data.size();
        if(isNotValid)
            System.out.println("Error : Duplicate Account Number on CSV file!");
        return isNotValid;
    };

    public void updateCSVOnTransaction(Account origin, Account dest, String command) {
        try {
            File oldFile = new File(FILE_INPUT_PATH);
            File newFile = new File(FILE_TEMP_PATH);
            List<List<String>> inputData = loadCSVFile();
            FileWriter csvWriter = generateCSVTemplate();
            if (command.equals(WITHDRAW)) {
                inputData.stream().forEach(p -> {
                    try {
                        if (p.get(3).equalsIgnoreCase(origin.getAccNumber())) {
                            p.set(2, String.valueOf(origin.getBalance()));
                        }
                        csvWriter.append(String.join(CSV_SEPARATOR, p));
                        csvWriter.append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            if (command.equals(FUND_TRANSFER)) {
                inputData.stream().forEach(p -> {
                    try {
                        if (p.get(3).equalsIgnoreCase(origin.getAccNumber()))
                            p.set(2, String.valueOf(origin.getBalance()));
                        if (p.get(3).equalsIgnoreCase(dest.getAccNumber()))
                            p.set(2, String.valueOf(dest.getBalance()));
                        csvWriter.append(String.join(CSV_SEPARATOR, p));
                        csvWriter.append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            csvWriter.flush();
            csvWriter.close();
            oldFile.delete();
            newFile.renameTo(oldFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileWriter generateCSVTemplate() throws IOException {
        FileWriter csvWriter = new FileWriter(FILE_TEMP_PATH);
        csvWriter.append("Name");
        csvWriter.append(",");
        csvWriter.append("PIN");
        csvWriter.append(",");
        csvWriter.append("Balance");
        csvWriter.append(",");
        csvWriter.append("Account Number");
        csvWriter.append("\n");

        return csvWriter;
    }

}
