package com.mitrais.atm.util;

import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    List<Account> userData = new ArrayList<>();

    public UserData() { }

    public List<Account> getUserData() {
        Account data1 = new Account("John Doe", "012108", 100, 112233);
        Account data2 = new Account("Jane Doe", "932012", 30, 112244);
        userData.add(data1);
        userData.add(data2);
        return userData;
    }

}
