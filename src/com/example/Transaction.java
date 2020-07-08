package com.example;

import java.util.Date;

public class Transaction {
    private Account account;
    private String type;
    private Date date;
    private double amount;

    public Transaction(Account account, String type, Date date, double amount) {
        this.account = account;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
