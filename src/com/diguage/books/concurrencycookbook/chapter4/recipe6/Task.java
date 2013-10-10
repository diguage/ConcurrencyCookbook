package com.diguage.books.concurrencycookbook.chapter4.recipe6;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * ScheduledExecutorService延迟执行示例：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-08
 * Time: 11:10
 */
public class Task implements Callable<String> {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.printf("%s: Starting at : %s\n",
                name, new Date());
        return "Hello, world!";
    }
}
