package com.diguage.books.concurrencycookbook.chapter4.recipe10;

import java.util.concurrent.CompletionService;

/**
 * 通过执行器分离任务的启动和结果的处理：报告申请类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-16
 * Time: 17:25
 */
public class ReportRequest implements Runnable {
    private String name;
    private CompletionService<String> service;

    public ReportRequest(String name, CompletionService<String> service) {
        this.name = name;
        this.service = service;
    }

    @Override
    public void run() {
        ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
        service.submit(reportGenerator);
    }
}
