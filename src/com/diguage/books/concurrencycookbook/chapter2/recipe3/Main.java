package com.diguage.books.concurrencycookbook.chapter2.recipe3;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 13-9-13
 * Time: 下午2:06
 */
public class Main {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Thread thread1 = new Thread(producer);

        Consumer consumer = new Consumer(storage);
        Thread thread2 = new Thread(consumer);

        thread1.start();
        thread2.start();
    }
}
