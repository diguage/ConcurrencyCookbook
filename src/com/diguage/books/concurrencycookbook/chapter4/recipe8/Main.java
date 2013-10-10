package com.diguage.books.concurrencycookbook.chapter4.recipe8;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 利用Future取消任务：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-10
 * Time: 17:08
 */
public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor)
                Executors.newCachedThreadPool();
        Task task = new Task();
        System.out.println("Main: Executing the Task");
        Future<String> result = executor.submit(task);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: Canceling the Task");
        result.cancel(true);
        System.out.printf("Main: Canceled: %s\n", result.isCancelled());
        System.out.printf("Main: Done: %s\n", result.isDone());
        executor.shutdown();
        System.out.println("Main: The executor has finished");
    }
}
