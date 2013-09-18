package com.diguage.books.concurrencycookbook.chapter1.recipe4;

import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 19:28
 */
public class Main {
    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch("C:\\", "autoexec.bat");
        Thread thread = new Thread(fileSearch);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

    }
}
