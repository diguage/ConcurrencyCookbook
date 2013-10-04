package com.diguage.books.concurrencycookbook.chapter2.recipe1;

/**
 * synchronized关键字示例：银行类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-04
 * Time: 23:59
 */
public class Bank implements Runnable {
    private Account account;

    public Bank(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.subtractAmount(1000);
        }
    }
}
