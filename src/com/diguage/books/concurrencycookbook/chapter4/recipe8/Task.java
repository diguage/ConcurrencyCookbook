package com.diguage.books.concurrencycookbook.chapter4.recipe8;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 利用Future取消任务：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-10
 * Time: 17:07
 */
public class Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        while (true) {
            System.out.println("Task: Test");
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
