package com.example;

import java.util.Date;

public class Account {
    private int accountNumber;
    private String name;
    private String gender;
    private String address;
    private double balance;
    private Date dateOpened;
    private String status;

    public Account(int accountNumber, String name, String gender, String address, double balance, Date dateOpened, String status) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.status = status;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
