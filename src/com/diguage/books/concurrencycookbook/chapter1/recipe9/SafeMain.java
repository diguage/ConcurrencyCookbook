package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.concurrent.TimeUnit;

/**
 * 安全的线程示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-24
 * Time: 00:04
 */
public class SafeMain {
    public static void main(String[] args) {
        SafeTask task = new SafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
