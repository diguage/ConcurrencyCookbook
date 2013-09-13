package com.diguage.books.concurrencycookbook.synchronization.conditions;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 13-9-13
 * Time: 下午1:07
 */
public class Consumer implements Runnable {
    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.get();
        }
    }
}
