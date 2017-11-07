package com.company;

public class Account {

    private int id;
    private int balance;
    private int transactions;

    public Account(int userID, int userBal) {
        this.id = userID;
        this.balance = userBal;
        this.transactions = 0;
    }

    public synchronized void deposite(Transaction t) {
        transactions++;
        balance += t.getAmount();
    }

    public synchronized void withdraw(Transaction t) {
        transactions++;
        balance -= t.getAmount();
    }


    @Override
    public String toString() {
        return "acct:" + id + " bal:" +  balance + " trans:" + transactions;
    }


}
