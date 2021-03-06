package com.mitrais.atm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account {
    @Id
    private String accNumber;
    private String name;
    private String pin;
    private int balance;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accNumber.equals(account.accNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", accNumber='" + accNumber + '\'' +
                '}';
    }
}
