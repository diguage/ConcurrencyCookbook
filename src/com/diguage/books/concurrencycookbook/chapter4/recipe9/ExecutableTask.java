package com.diguage.books.concurrencycookbook.chapter4.recipe9;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 通过FutureTask来控制任务的执行：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-10
 * Time: 17:15
 */
public class ExecutableTask implements Callable<String> {
    private String name;

    public ExecutableTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String call() throws Exception {
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Waiting %d seconds for results.\n",
                    this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello, world. I'm " + name;
    }
}
