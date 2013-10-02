package com.diguage.books.concurrencycookbook.chapter1.recipe10;

import java.util.concurrent.TimeUnit;

/**
 * 线程组示例主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-02
 * Time: 22:45
 */
public class Main {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Searcher");

        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.println("Information about the Thread Group");
        threadGroup.list();

        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(),
                    threads[i].getState());
        }

        waitFinish(threadGroup);

        threadGroup.interrupt();
    }

    // 等待任务完成
    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > 9) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
