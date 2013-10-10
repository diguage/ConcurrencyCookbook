package com.diguage.books.concurrencycookbook.chapter4.recipe6;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService延迟执行示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-08
 * Time: 11:12
 */
public class Main {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s\n", new Date());
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task-" + i);
            executor.schedule(task, i + 1, TimeUnit.SECONDS);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Ends at: %s\n", new Date());
    }
}
