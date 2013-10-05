package com.diguage.books.concurrencycookbook.chapter4.recipe1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 执行器Executor示例：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 22:10
 */
public class Task implements Runnable {
    private Date initDate;
    private String name;

    public Task(String name) {
        this.initDate = new Date();
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Task %s: Created on: %s\n",
                Thread.currentThread().getName(),
                name,
                new Date());
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Task %s: Doing a task during %d seconds\n",
                    Thread.currentThread().getName(),
                    name,
                    duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Task %s: Finished on: %s\n",
                Thread.currentThread().getName(),
                name,
                new Date());
    }
}
