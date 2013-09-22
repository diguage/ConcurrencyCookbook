package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 示例的主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:20
 */
public class Main {
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }
}
