package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserData {

    private static final String FILE_INPUT_PATH = "ATM-accounts.csv";
    private static final String CSV_SEPARATOR = ",";
    public UserData() { }

    public List<Account> getUserData() {
        List<Account> userData = new ArrayList<>();
        Account data1 = new Account("John Doe", "012108", 100, "112233");
        Account data2 = new Account("Jane Doe", "932012", 30, "112244");
        userData.add(data1);
        userData.add(data2);
        return userData;
    }

    public List<Account> getUserDataFromCSV() {
        List<Account> result = new ArrayList<>();
        try {
            File inputFile = new File(FILE_INPUT_PATH);
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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
        }
        return result;
    }

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

}
