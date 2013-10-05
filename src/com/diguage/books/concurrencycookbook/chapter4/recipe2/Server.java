package com.diguage.books.concurrencycookbook.chapter4.recipe2;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 固定数目的执行器Executor示例：服务器类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 22:18
 */
public class Server {
    private ThreadPoolExecutor executor;

    public Server() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    public void executeTask(Task task) {
        System.out.println("Server: A new task has arrived");
        executor.execute(task);
        System.out.printf("Server: Pool Size: %d\n",
                executor.getPoolSize());
        System.out.printf("Server: Active Count: %d\n",
                executor.getActiveCount());
        System.out.printf("Server: Task Count: %d\n",
                executor.getTaskCount());
        System.out.printf("Server: Completed Tasks: %d\n",
                executor.getCompletedTaskCount());
    }

    public void endServer() {
        executor.shutdown();
    }
}
