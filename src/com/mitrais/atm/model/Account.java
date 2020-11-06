package com.mitrais.atm.model;

public class Account {
    private String name;
    private String pin;
    private int balance;
    private int accNumber;

    public Account(String name, String pin, int balance, int accNumber) {
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

    public int getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }
}
