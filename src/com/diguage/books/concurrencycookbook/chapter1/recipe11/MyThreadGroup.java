package com.diguage.books.concurrencycookbook.chapter1.recipe11;

/**
 * 自定义的线程组类，用于自定义异常处理
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 01:21
 */
public class MyThreadGroup extends ThreadGroup {
    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("The thread %s has thrown an Exception\n",
                t.getId());
        e.printStackTrace(System.out);
        System.out.println("Terminating the rest of the Threads");
        interrupt();
    }


}
