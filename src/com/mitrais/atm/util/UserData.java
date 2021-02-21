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
        List<Account> result = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File inputFile = new File(FILE_INPUT_PATH);
            InputStream inputStream = new FileInputStream(inputFile);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            result = bufferedReader
                    .lines()
                    .skip(1) // skip header on CSV
                    .map(mapToAccount)
                    .collect(Collectors.toList());

            boolean isNotValid = validateCSVData(result);
            if(isNotValid)
                result = null;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            bufferedReader.close();
        }
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

    private Function<String, Account> mapToAccount = line -> {
      String[] p = line.split(CSV_SEPARATOR);
      Account account = new Account();
      account.setName(p[0]);
      account.setPin(p[1]);
      account.setBalance(Integer.valueOf(p[2]));
      account.setAccNumber(p[3]);
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



    public void updateCSVonWithdraw(Account account) {
        try {
            File oldFile = new File(FILE_INPUT_PATH);
            File newFile = new File(FILE_TEMP_PATH);
            List<List<String>> inputData = loadCSVFile();

            FileWriter csvWriter = new FileWriter(FILE_TEMP_PATH);
            csvWriter.append("Name");
            csvWriter.append(",");
            csvWriter.append("PIN");
            csvWriter.append(",");
            csvWriter.append("Balance");
            csvWriter.append(",");
            csvWriter.append("Account Number");
            csvWriter.append("\n");

            inputData.stream().forEach(p -> {
                try {
                    if (p.get(3).equalsIgnoreCase(account.getAccNumber())) {
                        p.set(2, String.valueOf(account.getBalance()));
                    }
                    csvWriter.append(String.join(CSV_SEPARATOR, p));
                    csvWriter.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            csvWriter.flush();
            csvWriter.close();
            oldFile.delete();
            newFile.renameTo(oldFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
