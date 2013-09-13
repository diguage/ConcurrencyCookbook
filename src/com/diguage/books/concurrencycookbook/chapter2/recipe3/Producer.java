package com.diguage.books.concurrencycookbook.chapter2.recipe3;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 13-9-13
 * Time: 下午1:06
 */
public class Producer implements Runnable {
    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.set();
        }
    }
}
