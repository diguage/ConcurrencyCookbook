package com.diguage.books.concurrencycookbook.chapter3.recipe1;

import java.util.concurrent.Semaphore;

/**
 * 演示Semaphore的使用
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-14
 * Time: 10:28
 */
public class PrintQueue {
    private final Semaphore semaphore;

    public PrintQueue() {
        this.semaphore = new Semaphore(1);
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",
                    Thread.currentThread().getName(), duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
