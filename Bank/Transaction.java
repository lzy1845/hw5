package com.company;

public class Transaction {
    private int fromAccount;
    private int toAccount;
    private int amount;

    public Transaction(int from, int to, int a) {
        this.fromAccount = from;
        this.toAccount = to;
        this.amount = a;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getFromAcc() {
        return this.fromAccount;
    }

    public int getToAcc() {
        return this.toAccount;
    }
}