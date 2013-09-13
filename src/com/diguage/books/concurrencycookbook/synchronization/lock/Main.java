package com.diguage.books.concurrencycookbook.synchronization.lock;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 下午2:28
 */
public class Main {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread " + i);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
