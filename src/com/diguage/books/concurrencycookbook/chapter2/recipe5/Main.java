package com.diguage.books.concurrencycookbook.chapter2.recipe5;

/**
 * ReadWriteLock示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 11:20
 */
public class Main {
    public static void main(String[] args) {
        PricesInfo pricesInfo = new PricesInfo();

        Reader[] readers = new Reader[5];
        Thread[] readersThreads = new Thread[readers.length];
        for (int i = 0; i < readersThreads.length; i++) {
            readers[i] = new Reader(pricesInfo);
            readersThreads[i] = new Thread(readers[i]);
        }

        Writer writers = new Writer(pricesInfo);
        Thread writersThread = new Thread(writers);

        for (int i = 0; i < readersThreads.length; i++) {
            readersThreads[i].start();
        }
        writersThread.start();
    }
}
