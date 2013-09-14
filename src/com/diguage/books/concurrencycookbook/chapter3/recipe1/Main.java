package com.diguage.books.concurrencycookbook.chapter3.recipe1;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-14
 * Time: 10:39
 */
public class Main {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread" + i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
