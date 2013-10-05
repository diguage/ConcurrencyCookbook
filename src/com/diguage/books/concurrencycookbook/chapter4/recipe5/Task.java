package com.diguage.books.concurrencycookbook.chapter4.recipe5;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Executor取得所有线程的处理结果示例：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 23:47
 */
public class Task implements Callable<Result> {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public Result call() throws Exception {
        System.out.printf("%s: Staring\n", this.name);
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Waiting %d seconds for results.\n",
                    this.name,
                    duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }
        Result result = new Result();
        result.setName(this.name);
        result.setValue(value);
        System.out.println(this.name + ": Ends");
        return result;
    }
}
