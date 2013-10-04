package com.diguage.books.concurrencycookbook.chapter2.recipe1;

import java.util.concurrent.TimeUnit;

/**
 * synchronized关键字示例：账户类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-04
 * Time: 23:52
 */
public class Account {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // 增加存款
    public synchronized void addAmount(double amount) {
        double tmp = balance;
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp += amount;
        this.balance = tmp;
    }

    // 减少存款
    public synchronized void subtractAmount(double amount) {
        double tmp = this.balance;
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp -= amount;
        this.balance = tmp;
    }
}
