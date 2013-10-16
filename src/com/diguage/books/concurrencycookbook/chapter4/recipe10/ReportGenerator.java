package com.diguage.books.concurrencycookbook.chapter4.recipe10;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 通过执行器分离任务的启动和结果的处理：报告生成类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-10
 * Time: 17:33
 */
public class ReportGenerator implements Callable<String> {
    private String sender;
    private String title;

    public ReportGenerator(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }

    @Override
    public String call() throws Exception {
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s_%s: ReportGenerator: Generating a report during %d seconds\n",
                    this.sender, this.title, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.sender + ": " + this.title;
    }
}
