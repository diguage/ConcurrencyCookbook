package com.diguage.books.concurrencycookbook.chapter1.recipe12;

/**
 * 线程工厂示例主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 19:34
 */
public class Main {
    public static void main(String[] args) {
        MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
        Task task = new Task();
        Thread thread;
        for (int i = 0; i < 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
        System.out.println("Factory stats:");
        System.out.printf("%s\n", factory.getStats());
    }
}
