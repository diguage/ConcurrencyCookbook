package com.diguage.books.concurrencycookbook.chapter4.recipe1;

/**
 * 执行器Executor示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 22:22
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        for (int i = 0; i < 100; i++) {
            Task task = new Task("Task-" + i);
            server.executeTask(task);
        }
        server.endServer();
    }
}
