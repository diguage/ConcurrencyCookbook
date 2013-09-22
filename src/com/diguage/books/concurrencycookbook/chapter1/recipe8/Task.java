package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 异常生成类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:18
 */
public class Task implements Runnable {
    @Override
    public void run() {
        int numero = Integer.parseInt("diguage.com");
    }
}
