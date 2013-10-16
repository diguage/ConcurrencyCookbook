package com.diguage.books.concurrencycookbook.chapter4.recipe10;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 通过执行器分离任务的启动和结果的处理：报告处理类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-16
 * Time: 17:27
 */
public class ReportProcessor implements Runnable {
    private CompletionService<String> service;
    private boolean end;

    public ReportProcessor(CompletionService<String> service) {
        this.service = service;
        this.end = false;
    }

    @Override
    public void run() {
        while (!end) {
            try {
                Future<String> result = service.poll(20, TimeUnit.SECONDS);
                if (null != result) {
                    String report = result.get();
                    System.out.printf("ReportReceiver: Report Received: %s\n", report);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ReportSender: End\n");
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
