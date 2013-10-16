package com.diguage.books.concurrencycookbook.chapter4.recipe11;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 执行器拒绝任务：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-16
 * Time: 23:08
 */
public class Main {
    public static void main(String[] args) {
        RejectedTaskController controller = new RejectedTaskController();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setRejectedExecutionHandler(controller);

        System.out.println("Main: Starting.");
        for (int i = 0; i < 3; i++) {
            Task task = new Task("Task-" + i);
            executor.submit(task);
        }

        System.out.println("Main: Shutting down the Executor.");
        executor.shutdown();

        System.out.println("Main: Sending another Task.");
        Task task = new Task("RejectedTask");
        executor.submit(task);

        System.out.println("Main: End");  // 原文这里多打印了一行，应该是个错误。
    }
}
