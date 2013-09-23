package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-23
 * Time: 23:58
 */
public class UnsafeTask implements Runnable {
    private Date startDate;

    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate);

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate);
    }
}
