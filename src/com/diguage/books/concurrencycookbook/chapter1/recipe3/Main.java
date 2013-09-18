package com.diguage.books.concurrencycookbook.chapter1.recipe3;

import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 12:33
 */
public class Main {
    public static void main(String[] args) {
        Thread task = new PrimeGenerator();
        task.start();

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task.interrupt();
    }
}
