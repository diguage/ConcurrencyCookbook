package com.diguage.books.concurrencycookbook.chapter1.recipe11;

import java.util.Random;

/**
 * 线程类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-04
 * Time: 00:32
 */
public class Task implements Runnable {
    @Override
    public void run() {
        int result;
        Random random = new Random(Thread.currentThread().getId());
        while (true) {
            result = 1000 / ((int) (random.nextDouble() * 1000));
            System.out.printf("%s : %f\n",
                    Thread.currentThread().getId(), result);
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("%d : Interrupted\n",
                        Thread.currentThread().getId());
                return;
            }
        }
    }
}
