package com.diguage.books.concurrencycookbook.chapter4.recipe7;

import java.util.Date;

/**
 * ScheduledExecutorService定期执行示例：任务类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-10
 * Time: 16:51
 */
public class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Starting at : %s\n", name, new Date());  // 文章中的代码有误

    }
}
