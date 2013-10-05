package com.diguage.books.concurrencycookbook.chapter2.recipe7;

/**
 * 条件锁示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 11:59
 */
public class Main {
    public static void main(String[] args) {
        FileMock mock = new FileMock(100, 10);
        Buffer buffer = new Buffer(20);

        Producer producer = new Producer(mock, buffer);
        Thread producerThread = new Thread(producer, "Producer");

        Consumer[] consumers = new Consumer[3];
        Thread[] consumersThreads = new Thread[consumers.length];
        for (int i = 0; i < consumersThreads.length; i++) {
            consumers[i] = new Consumer(buffer);
            consumersThreads[i] = new Thread(consumers[i], "Consumer " + i);
        }

        producerThread.start();
        for (int i = 0; i < consumersThreads.length; i++) {
            consumersThreads[i].start();
        }
    }
}
