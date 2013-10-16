package com.diguage.books.concurrencycookbook.chapter4.recipe10;

import java.util.concurrent.*;

/**
 * 通过执行器分离任务的启动和结果的处理：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-16
 * Time: 17:34
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<String> service = new ExecutorCompletionService<String>(executor);

        ReportRequest faceRequest = new ReportRequest("Face", service);
        ReportRequest onlineRequest = new ReportRequest("Online", service);
        Thread faceThread = new Thread(faceRequest);
        Thread onlineThread = new Thread(onlineRequest);

        ReportProcessor processor = new ReportProcessor(service);
        Thread processThread = new Thread(processor);

        System.out.println("Main: Starting the Threads");
        faceThread.start();
        onlineThread.start();
        processThread.start();

        try {
            System.out.println("Main: Waiting for the report generators.");
            faceThread.join();
            onlineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main: Shutting down the executor.");
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processor.setEnd(true);
        System.out.println("Main: Ends");
    }
}
