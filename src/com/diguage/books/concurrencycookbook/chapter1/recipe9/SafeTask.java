package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-29
 * Time: 23:34
 */
public class SafeTask implements Runnable {
    private static ThreadLocal<Date> startDate = new
            ThreadLocal<Date>() {
                @Override
                protected Date initialValue() {
                    return new Date();
                }
            };

    @Override
    public void run() {
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());
    }
}
