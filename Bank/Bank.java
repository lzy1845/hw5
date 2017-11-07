package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
    private ArrayList<Account> accounts;
    private ArrayBlockingQueue<Transaction> queue;
    private final int nACCOUNTS = 20;
    private final int initialBal = 1000;
    private final Transaction nullTrans = new Transaction(-1, 0, 0);
    private int numThreads;
    static CountDownLatch latch;

    public class Worker extends Thread {
        public void run() {
            while (true) {
                try {
                    Transaction newTrans = queue.take();
                    if (newTrans == nullTrans) {
                        break;
                    }
                    accounts.get(newTrans.getFromAcc()).withdraw(newTrans);
                    accounts.get(newTrans.getToAcc()).deposite(newTrans);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }
    }

    public Bank (int numThreads) {
        this.accounts = new ArrayList<Account>();
        this.queue = new ArrayBlockingQueue<Transaction>(nACCOUNTS);
        this.numThreads = numThreads;
        for (int i = 0; i < nACCOUNTS; i++) {
            accounts.add(new Account(i, initialBal));
        }
        for (int i = 0; i < this.numThreads; i++) {
            new Worker().start();
        }
    }

    public void readFile(String name) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            String line = reader.readLine();
            String[] s;
            int fromAcc, toAcc, amount;
            while (line != null) {
                s = line.split(" ");
                fromAcc = Integer.parseInt(s[0]);
                toAcc = Integer.parseInt(s[1]);
                amount = Integer.parseInt(s[2]);
                Transaction newTrans = new Transaction(fromAcc, toAcc, amount);
                queue.put(newTrans);
                line = reader.readLine();
            }
            reader.close();
            for (int i = 0; i < this.numThreads; i++) {
                queue.put(nullTrans);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printBank() {
        for (int i = 0; i < nACCOUNTS; i++) {
            System.out.println(accounts.get(i).toString());
        }
    }


    public static void main(String[] args) {
        if (args.length < 2) throw new RuntimeException("Input is wrong");
        int nThreads = Integer.parseInt(args[1]);
        String fileName = args[0];

        latch = new CountDownLatch(nThreads);
        Bank mybank = new Bank(nThreads);
        mybank.readFile(fileName);

        try{
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mybank.printBank();
    }
}








