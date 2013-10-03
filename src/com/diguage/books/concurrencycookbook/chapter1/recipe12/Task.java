package com.diguage.books.concurrencycookbook.chapter1.recipe12;

import java.util.concurrent.TimeUnit;

/**
 * 线程类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 19:19
 */
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
