package com.mitrais.atm.model;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String name;
    private String pin;
    private int balance;
    private String accNumber;

    public Account(String name, String pin, int balance, String accNumber) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accNumber = accNumber;
    }

    public Account(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
