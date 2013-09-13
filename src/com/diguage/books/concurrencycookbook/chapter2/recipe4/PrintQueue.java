package com.diguage.books.concurrencycookbook.chapter2.recipe4;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 下午2:18
 */
public class PrintQueue {
    private final Lock queueLock = new ReentrantLock();

    public void printJob(Object document) {
        queueLock.lock();
        Random random = new Random();
        try {
            Long duration = Long.valueOf(random.nextInt(10000));
            System.out.println(Thread.currentThread().getName() +
                    ": PrintQueue: Printing a Job during " + (duration / 1000) + " seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}
