package com.diguage.books.concurrencycookbook.chapter4.recipe5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Executor取得所有线程的处理结果示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 23:53
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Task> taskList = new ArrayList<Task>();
        for (int i = 0; i < 3; i++) {
            Task task = new Task("Task-" + i); //原文这里错误
            taskList.add(task);
        }

        List<Future<Result>> resultList = null;
        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        System.out.println("Main: Printing the results");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Result> future = resultList.get(i);
            try {
                Result result = future.get();
                System.out.println(result.getName() + ": " + result.getValue());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
