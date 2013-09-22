package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 非受检异常处理类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:11
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s\n", t.getId());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(),
                e.getMessage());
        System.out.printf("Stack Trace: \n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n", t.getState());
    }
}
